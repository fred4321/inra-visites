<%
    def nature = Params.findWhere(groupe:"natureVM", valeur : vm.natureVM)
    def nature_text = nature ? nature.info1 : vm.natureVM
%>
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

            table {
                width:100%;
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
            h2 {font-size: 18px;}

            div.checkbox {
                width: 16px; height: 16px; border:1px solid #999;
                display:block;
                text-align: center;
                float:left;
                margin: 0 8px 0 15px;
            }
        </style>
        <title>Fiche de visite médicale</title>
    </head>
    <body>
        <div>
            <img src="/img/republique-francaise.png" alt="logo INRA" style="width:2cm;"/>
            <img src="/img/logo-INRA-transp-small.png" alt="logo INRA" style="width:3.5cm;float:right;"/>
        </div>
        <h2>
            Fiche de visite de médecine de prévention n° ${vm.id}
        </h2>
        <table>
            <tr><td colspan="2" style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;"><h4>AGENT N° ${vm.matricule}</h4></td></tr>
            <tr>
                <td style="width: 50%">Date de VM : <g:formatDate format="dd MMM yyyy" date="${vm.date}"/></td>
                <td style="width: 50%">Statut : ${vm.statut}</td>
            </tr>
            <tr>
                <td><b>Nom : </b>${vm.nom} ${vm.prenom}</td>
                <td>Né(e) le : <g:formatDate format="dd MMM yyyy" date="${vm.dateNaissance}"/></td>                
            </tr>
            <tr>
                <td><b>Unité : </b>${vm.uniteAgent}</td>
                <td>Emploi : ${vm.libEmploi}</td>
            </tr>
            <tr>
                <td>Site : ${vm.site}</td>
                <td>Médecin : ${vm.refMedecin }</td>
            </tr>
            <tr>
                <td colspan="2">Arrêt : <g:formatDate format="dd MMM yyyy" date="${vm.dateArretTravail}"/></td>
            </tr>
            <tr>
                <td colspan="2" style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;"><h4>AVIS</h4></td>
            </tr>
            
            <tr>
                <td colspan="2" style="padding-bottom:10px;">
                    <span style="float:left"><b>Vu  </b> </span>                  
                    <div class="checkbox">${vm.vu=='présentiel' ? 'X' : ''}</div> <span style="float:left">en présentiel</span>
                    <div class="checkbox">${vm.vu=='téléconsultation' ? 'X' : ''}</div> <span style="float:left;">en téléconsultation</span>
                    <g:if test="${vm.vuPar}"><span style="margin-left: 20px;">par ${vm.vuPar}</span></g:if>
                </td>
            </tr>
            
            <tr>
                <td><b>Apte : <g:if test="${vm.apte != "null"}">${vm.apte == "temporaire" ? "Inapte temporairement" : vm.apte }</g:if></b></td>
                <td><b>Nature</b> : ${nature_text}</td>
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
                    <p style="width: 60mm; padding: 0; margin: 0; text-align:left;"><b>Courriel Agent : </b><g:formatDate format="dd MMM yyyy" date="${vm.dateEnvoisCourrierAgent}"/></p>
                </td>
                <td>
                    <p style="width: 50mm; padding: 0; margin: 0;"><b>Courriel DU + RRH : </b><g:formatDate format="dd MMM yyyy" date="${vm.dateEnvoisCourrierDURRH}"/></p>
                </td>
            </tr>
        </table>


        <g:if test="${vm.dateProchaineVm}">
            <p>
                À revoir avant le <b><g:formatDate format="dd MMM yyyy" date="${vm.dateProchaineVm}"/> </b>
                en <b>${vm.vuProchaineVm}</b> par <b>${vm.vuParProchaineVm}</b>
            </p>
        </g:if>


        <p>Cachet et signature du médecin:</p>
        <br/><br/><br/><br/>
        <p><i>Pensez ENVIRONNEMENT : n'imprimez que si nécessaire</i></p>
    </body>
</html>