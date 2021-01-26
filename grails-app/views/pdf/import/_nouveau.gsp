<h4>NOUVEAUX AGENTS: ${nouveau.size()}</h4>
<ul>
    <g:each in="${nouveau}" var="i" status="cpt">
        <li>NOUVEAU : Agent ${i.matricule} ${i.prenom} ${i.nom}. Ligne ${i.row}</li>
    </g:each>
</ul>