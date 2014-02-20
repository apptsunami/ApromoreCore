<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:epml = "http://www.epml.de"
  xmlns:xsl  = "http://www.w3.org/1999/XSL/Transform">

<xsl:variable name="ids" select="//@id | //@epcId"/>

<!-- Calculate the maximum id, so we can generate new unique ones -->
<xsl:variable name="max-id">
    <xsl:for-each select="$ids">
        <xsl:sort data-type="number"/>
        <xsl:if test="position() = last()">
            <xsl:value-of select="current()"/>
        </xsl:if>
    </xsl:for-each>
</xsl:variable>

<xsl:variable name="max-arc-id">
    <xsl:for-each select="//arc/@id">
        <xsl:sort data-type="number"/>
        <xsl:if test="position() = last()">
            <xsl:value-of select="current()"/>
        </xsl:if>
    </xsl:for-each>
</xsl:variable>

<!-- If the top level <epml> element isn't namespaced, add the right namespace -->
<!-- If there's no top level <coordinates>, insert one -->
<!-- If there's no top level <directory>, insert one -->
<xsl:template match="/epml:epml | /epml">
    <xsl:comment>
    </xsl:comment>
    <epml:epml>
        <coordinates xOrigin="leftToRight" yOrigin="topToBottom"/>
        <xsl:choose>
        <xsl:when test="not(directory)">
            <directory>
                <xsl:apply-templates select="epc"/>
            </directory>
        </xsl:when>
        <xsl:otherwise>
            <xsl:apply-templates select="directory"/>
        </xsl:otherwise>
        </xsl:choose>
    </epml:epml>
</xsl:template>

<!-- If an <epc> element has no @epcID but does have an @id, use that instead -->
<xsl:template match="epc[not(@epcId)]">
    <xsl:copy>
        <xsl:call-template name="epcId-template">
            <xsl:with-param name="epcId-param" select="@id"/>
        </xsl:call-template>
        <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
</xsl:template>

<!-- Drop the epc/@id attribute if we're replacing it with epc/@epcId -->
<xsl:template match="epc[not(@epcId)]/@id"/>

<!-- The "normal" case for <epc> elements, which checks whether they need renumbering to avoid clashing -->
<xsl:template match="epc/@epcId" name="epcId-template">
    <xsl:param name="epcId-param" select="current()"/>
    <xsl:attribute name="epcId">
        <xsl:choose>
        <xsl:when test="contains($epcId-param, $ids)">
            <xsl:value-of select="$max-id + $max-arc-id + $epcId-param"/>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$epcId-param"/>
        </xsl:otherwise>
        </xsl:choose>
    </xsl:attribute>
</xsl:template>

<!-- Use an identity template so that everything that doesn't need workarounds gets passed through unchanged. -->
<xsl:template match="@*|node()">
    <xsl:copy>
        <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
</xsl:template>

<!-- Renumber arcs if their id is the same as any event -->
<xsl:template match="arc[@id = //epc/*[name() != 'arc']/@id]/@id">
    <xsl:attribute name="id">
        <xsl:value-of select="current() + $max-id"/>
    </xsl:attribute>
</xsl:template>

</xsl:stylesheet>