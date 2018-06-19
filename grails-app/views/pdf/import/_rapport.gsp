<%@page defaultCodec="none" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
        <title>Rapport d'import</title>
    </head>
    <body>
        <g:if test="${resume=="true"}">
            <g:render template="import/resume" model="['rapport' : rapport]" />
        </g:if>
        <g:if test="${nouveau=="true"}">
            <g:render template="import/nouveau" model="['nouveau' : rapport.nouveau]" />
        </g:if>
        <g:if test="${modifier=="true"}">
            <g:render template="import/modifier" model="['modifier' : rapport.modifier]" />
        </g:if>
        <g:if test="${prioritaire=="true"}">
            <g:render template="import/prioritaire" model="['prioritaire' : rapport.prioritaire]" />
        </g:if>
        <g:if test="${erreur=="true"}">
            <g:render template="import/erreur" model="['erreur' : rapport.erreur]" />   
        </g:if>
    </body>
</html>
