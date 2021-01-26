<h2>Rapport d'import</h2>
<table>
    <tr>
        <td style="width: 24%;"><b>N° : </b>${rapport.id}</td>
        <td style="width: 24%;"><b>Date: </b><g:formatDate format="dd MMM yyyy" date="${rapport.date}" /></td>
        <td style="width: 24%;"><b>Type: </b>${rapport.type}</td>
        <td style="width: 24%;"><b>erreurs: </b>${rapport.erreur.size()}</td>
    </tr>
    <tr>
        <td style="width: 24%;"><b>Modifiées: </b>${rapport.modifier.size()}</td>
        <td style="width: 24%;"><b>Nouveaux agents: </b>${rapport.nouveau.size()}</td>
        <td style="width: 24%;"><b>Agents prioritaires: </b>${rapport.prioritaire.size()}</td>
    </tr>
</table>