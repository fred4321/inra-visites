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
        <title>fiche Agent n° ${agent.id}</title>
    </head>
    <body>
        <img src="web-app/img/logo-INRA-transp-small.png" alt="logo INRA" style="width:3.5cm;height:1.4cm;float:right;"/>
        <h2 style="border-bottom: #333333;border-bottom-style: solid;border-bottom-width: 0.1mm;padding-top: 1cm;">
            FICHE AGENT
        </h2>
        <table>
            <tr>
                <td style="width: 33.3333%"><b>Matricule : </b>${agent.matricule}</td>
                <td style="width: 33.3333%"><b>Genre : </b>${agent.genre}</td>
                <td style="width: 33.3333%"><b>INSEE : </b>${agent.insee}</td>
            </tr>
            <tr>
                <td style="width: 33.3333%"><b>Nom : </b>${agent.nom}</td>
                <td style="width: 33.3333%"><b>Prénom : </b>${agent.prenom}</td>                
                <td style="width: 33.3333%"><b>Né(e) le : </b><g:formatDate format="dd MMM yyyy" date="${agent.dateNaissance}"/></td>
            </tr>
            <tr>
                <td><b>Né(e) à : </b>${agent.lieuNaissance}</td>
                <td><b>Dépt : </b>${agent.departement}</td>
                <td><b>Nationalité : </b>${agent.nationalite}</td>
            </tr>
            <tr>
                <td><b>Unité : </b>${agent.unite}</td>
                <td><b>Emploi : </b>${agent.posteTravail}</td>
                <td><b>GU : </b>${agent.gu}</td>
            </tr>
            <tr>
                <td><b>Site : </b>${agent.site}</td>
                <td><b>Entrée : </b><g:formatDate format="dd MMM yyyy" date="${agent.dateEntree}"/></td>
                <td><b>Mail GU : </b>${agent.gu ? agent.gu.email : ''}</td>
            </tr>
            <tr>
                <td><b>Périodicité : </b>${agent.periodicite}</td>
                <td><b>Dernière VM : </b>todo</td>
                <td><b>Mail : </b>${agent.courriel}</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td><b>Tél : </b>${agent.numTel}</td>
            </tr>
        </table>
        <h4 style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;">VISITE MÉDICALE</h4>
        <table>
            <tr>
                <th style="width: 35mm">Date VM</th>
                <th style="width: 25mm">Fiche</th>
                <th style="width: 25mm">Statut</th>
                <th style="width: 25mm">Nature</th>
                <th style="width: 35mm">Fin d'Arrêt</th>
                <th style="width: 35mm">Courriel</th>
            </tr>
            <g:each in="${agent.vm}" var="vm" status="cpt">
                <tr>
                    <td><g:formatDate format="dd MMM yyyy" date="${vm.date}"/></td>
                    <td>N° : ${vm.id}</td>
                    <td>${vm.statut}</td>
                    <td>${vm.natureVM}</td>
                    <td><g:formatDate format="dd MMM yyyy" date="${agent.dateArretTravail}"/></td>
                    <td><g:formatDate format="dd MMM yyyy" date="${vm.dateEnvoisCourrierAgent}"/></td>
                </tr>
            </g:each>
        </table>
        <h4 style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;">BILAN SANGUIN</h4>
        <table>
            <thead>
                <tr>
                    <th style="width: 35mm">Prescription</th>
                    <th style="width: 35mm">Date BS</th>
                    <th style="width: 25mm">Fiche</th>
                    <th style="width: 25mm">Statut</th>
                    <th style="width: 35mm">Convocation</th>
                </tr>
            </thead>
            <tbody>
                <g:each in="${agent.bs}" var="bs" status="cpt">
                    <tr>
                        <td><g:formatDate format="dd MMM yyyy" date="${bs.datePrescription}"/></td>
                        <td><g:formatDate format="dd MMM yyyy" date="${bs.datePassage}"/></td>
                        <td>N° : ${bs.id}</td>
                        <td>${bs.statut}</td>
                        <td><g:formatDate format="dd MMM yyyy" date="${bs.dateConvocation}"/></td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <p style="border-top: #333333;border-top-style: solid;border-top-width: 0.1mm;">
            <b>Nature VM : </b>${agent.natureVM}<br/>
            <b>Agent prioritaire : </b><g:if test="${agent.prioritaireVM}">OUI</g:if><g:else>NON</g:else>
        </p>
        <p>
            <b>Créée le : </b><g:formatDate format="dd MMM yyyy" date="${agent.dateCreation}"/><br/>
            <b>Modifiée le : </b><g:formatDate format="dd MMM yyyy" date="${agent.dateModification}"/>
        </p>
    </body>
</html>
