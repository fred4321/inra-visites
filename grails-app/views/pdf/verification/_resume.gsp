<h2>Rapport de vérification</h2>
<table>
    <tr>
        <td style="width: 24%;"><b>N° : </b>${verif.id}</td>
        <td style="width: 24%;"><b>Date: </b><g:formatDate format="dd MMM yyyy" date="${verif.date}" /></td>
        <td style="width: 24%;"><b>Type: </b>${verif.type}</td>
        <td style="width: 24%;"><b>Vide: </b>${verif.vide.size()}</td>
    </tr>
    <tr>
        <td style="width: 24%;"><b>Invalides: </b>${verif.invalide.size()}</td>
        <td style="width: 24%;"><b>Doublons champs: </b>${verif.champsLength}</td>
        <td style="width: 24%;"><b>Doublons lignes: </b>${verif.ligneLength}</td>
        <td style="width: 24%;"><b>Erreurs lignes: </b>${verif.ligneLength + verif.champsLength + verif.invalide.size() + verif.vide.size()}</td>
    </tr>
</table>