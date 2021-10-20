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
        </style>
        <title>${title}</title>
    </head>
    <body>
        <div>
            <img src="/img/republique-francaise.png" alt="logo INRA" style="width:2cm;"/>
            <img src="/img/logo-INRA-transp-small.png" alt="logo INRA" style="width:3.5cm;float:right;"/>
        </div>

        <table>
            <tr>
                <g:each in="${titles}" var="t" >
                    <td>${t}</td>
                </g:each>
            </tr>
            <g:each in="${data}" var="row" status="i">
                <tr>
                    <g:each in="${(1..colonne)}" var="j" >
                        <td>${row[j]}</td>
                    </g:each>
                </tr>
            </g:each>
        </table>
    </body>
</html>
