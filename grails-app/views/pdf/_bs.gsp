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
        </style>
        <title>Fiche de bilan sanguin</title>
    </head>
    <body>
        <div>
            <img src="${ pdf ? "web-app" : "" }/img/republique-francaise.png" alt="logo INRA" style="width:2cm;"/>
            <img src="${ pdf ? "web-app" : "" }/img/logo-INRA-transp-small.png" alt="logo INRA" style="width:3.5cm;float:right;"/>
        </div>

        <h2 style="border-bottom: #333333;border-bottom-style: solid;border-bottom-width: 0.1mm;">
            Bilan sanguin N° ${bs.id}
        </h2>
        <table>
            <tr><td colspan="3" style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;"><h4>AGENT N° ${bs.matricule}</h4></td></tr>
            <tr>
                <td style="width: 33.3333%"><b>Nom : </b>${bs.nom}</td>
                <td style="width: 33.3333%"><b>Prénom : </b>${bs.prenom}</td>                
                <td style="width: 33.3333%"><b>Né(e) le : </b>${bs.dateNaissance}</td>
            </tr>
            <tr>
                <td><b>Site : </b>${bs.site}</td>
                <td><b>Poste : </b>${bs.posteTravail}</td>
                <td><b>Unité : </b>${bs.unite}</td>
            </tr>
            <tr><td colspan="3" style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;"><h4>PRESCRIPTION</h4></td></tr>
            <tr>
                <td><b>Type de bilan sanguin prescrit : </b></td>
                <td colspan="2">${bs.type}</td>
            </tr>
            <tr><td colspan="3"><h5>REMARQUES BS</h5></td></tr>
            <g:each in="${bs.remarques}" var="remarque" status="cpt">
                <tr><td colspan="3">${remarque.text}</td></tr>
            </g:each>

        </table>
    </body>
</html>
