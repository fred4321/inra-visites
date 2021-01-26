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
        <title>Fiche de visite médical</title>
    </head>
    <body>
        <img src="${ pdf ? "web-app" : "" }/img/logo-INRA-transp-small.png" alt="logo INRA" style="width:3.5cm;float:right;"/>
        <h2 style="padding-top: 1cm;">
            Visite médicale N° ${vm.id}
        </h2>
        <table>
            <tr><td colspan="2" style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;"><h4>AGENT N° ${vm.matricule}</h4></td></tr>
            <tr>
                <td style="width: 50%">Date de VM : <g:formatDate format="dd MMM yyyy" date="${vm.date}"/></td>
                <td style="width: 50%">Statut : ${vm.statut}</td>
            </tr>
            <tr>
                <td><b>Nom : </b>${vm.nom} ${vm.prenom}</td>
                <td>Médecin : ${vm.refMedecin}</td>
            </tr>
            <tr>
                <td><b>Unité : </b>${vm.uniteAgent}</td>
                <td>Né(e) le : <g:formatDate format="dd MMM yyyy" date="${vm.dateNaissance}"/></td>                
            </tr>
            <tr>
                <td>Site : ${vm.site}</td>
                <td>Emploi : ${vm.libEmploi}</td>
            </tr>
            <tr>
                <td colspan="2">Arrêt : <g:formatDate format="dd MMM yyyy" date="${vm.dateArretTravail}"/></td>
            </tr>
            <tr>
                <td colspan="2" style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;"><h4>AVIS</h4></td>
            </tr>
            <tr>
                <td><b>Apte : <g:if test="${vm.apte != "null"}">${vm.apte == "temporaire" ? "Inapte temporairement" : vm.apte }</g:if></b></td>
                <td>Nature : ${vm.natureVM}</td>
            </tr>
            <tr>
                <td colspan="2"><br/><g:if test="${vm.amenagement}">Travail réalisable sans aménagement particulier autre que les règles communes de prévention adaptées au poste : <b>OUI</b></g:if><br/></td>
            </tr>
            <g:if test="${!(DU)}">
                <tr><td colspan="2"><h5>OBSERVATIONS :</h5></td></tr>
                <g:each in="${vm.observations}" var="observation" status="cpt">
                    <tr><td colspan="2" style="padding-bottom:10px">- ${observation.text}</td></tr>
                </g:each>
            </g:if>
            
            <g:if test="${user.profil != "GU"}">
                <tr><td colspan="2" style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;padding-bottom: 0;margin-bottom: 0;"><h4 style="padding-bottom: 0;margin-bottom: 0;">PRESCRIPTION</h4></td></tr>            
                <tr><td colspan="2"><h5>AUTRES CONCLUSIONS :</h5></td></tr>
                <g:each in="${vm.conclusions}" var="conclusion" status="cpt">
                    <tr><td colspan="2" style="padding-bottom:10px">- ${conclusion.text}</td></tr>
                </g:each>
            </g:if>
            <g:if test="${user.profil!="GU" && vm.typeBs && !(DU)}">
                <tr><td colspan="2" style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;padding-bottom: 0;margin-bottom: 0;"><h4 style="padding-bottom: 0;margin-bottom: 0;">BILAN SANGUIN</h4></td></tr>
                <tr>
                    <td colspan="2"><b>Type de bilan sanguin prescrit : </b></td>
                    
                </tr>
                <g:each in="${vm.typeBs.split('_')}" var="type" status="i">
                    <g:if test="${(i % 2) == 0}"><tr></g:if>
                        <td>- ${type}</td>
                    <g:if test="${(i % 2) == 1 || i == vm.typeBs.split('_').size()-1 }"></tr></g:if>
                </g:each>
                <tr><td colspan="2"><h5>REMARQUES BS :</h5></td></tr>
                <g:each in="${vm.remarques}" var="remarque" status="cpt">
                    <tr><td colspan="2" style="padding-bottom:10px">- ${remarque.text}</td></tr>
                </g:each>
            </g:if>
            
        </table>
        <p style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm; padding: 0; margin: 0;"><br/><b>Dernière VM : </b><g:if test="${vm.dateDerniereVM}"><g:formatDate format="dd MMM yyyy" date="${vm.dateDerniereVM}"/></g:if></p>
        <table>
            <tr>
                <td>
                    <p style="width: 60mm; padding: 0; margin: 0;"><b>Courriel Agent : </b><g:formatDate format="dd MMM yyyy" date="${vm.dateEnvoisCourrierAgent}"/></p>
                </td>
                <td>
                    <p style="width: 50mm; padding: 0; margin: 0;"><b>Courriel DU + RRH : </b><g:formatDate format="dd MMM yyyy" date="${vm.dateEnvoisCourrierDURRH}"/></p>
                </td>
            </tr>
        </table>
        <p>Cachet et signature du médecin:</p>
        <br/><br/><br/><br/>
        <p><i>Pensez ENVIRONNEMENT : n'imprimez que si nécessaire</i></p>
    </body>
</html>