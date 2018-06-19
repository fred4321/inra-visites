<h4>ERREURS : ${erreur.size()}</h4>
<ul>
    <g:each in="${erreur}" var="i" status="cpt">
        <li>ERREUR : Agent ${i.matricule}. Ligne ${i.row} : ${i.erreur}</li>
    </g:each>
</ul>