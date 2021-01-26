<h4>AGENTS PRIORITAIRES : ${prioritaire.size()}</h4>
<ul>
    <g:each in="${prioritaire}" var="i" status="cpt">
        <li>PRIORITAIRE : Agent ${i.matricule} ${i.prenom} ${i.nom}. EN LISTE PRIORITAIRE NATURE de Visite ${i.info}</li>
    </g:each>
</ul>