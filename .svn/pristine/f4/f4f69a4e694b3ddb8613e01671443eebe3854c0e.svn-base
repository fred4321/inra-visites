<%@page defaultCodec="none" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <style>
            @page {
            size: A4;
            margin: 15.15mm 7.75mm 0 7.75mm;
            }
            .brk {
            page-break-before:always;
            }

            .spacer {
            float:left;
            width:2mm;
            height:38.1mm;
            line-height:38.1mm;
            }

            h1,h2,h3,h4,h5,h6{
            text-transform: uppercase;
            }
            ul{
                list-style: none;
            }
        </style>
        <title>Rapport de vérification</title>
    </head>
    <body>
        <g:if test="${resume=="true"}">
            <g:render template="verification/resume" model="['verif' : verification]" />
        </g:if>
        <g:if test="${vides=="true"}">
            <g:render template="verification/vides" model="['vides' : verification.vide]" />
        </g:if>
        <g:if test="${invalides=="true"}">
            <g:render template="verification/invalides" model="['invalides' : verification.invalide]" />
        </g:if>
        <g:if test="${champs=="true"}">
            <g:render template="verification/champs" model="['champs' : verification.champs, 'length' : verification.champsLength]" />
        </g:if>
        <g:if test="${lignes=="true"}">
            <g:render template="verification/lignes" model="['lignes' : verification.lignes, 'length' : verification.ligneLength]" />   
        </g:if>
        <g:if test="${DBB=="true"}">
            <g:render template="verification/dbb" model="['db' : verification.DBB]" />   
        </g:if>
    </body>
</html>
