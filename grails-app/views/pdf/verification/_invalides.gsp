<h4>Invalides: ${invalides.size()}</h4>
<ul>
    <g:each in="${invalides}" var="i" status="cpt">
        <li>Ligne ${i.row}: le champ "${i.dataType}" est invalide.</li>
    </g:each>
</ul>