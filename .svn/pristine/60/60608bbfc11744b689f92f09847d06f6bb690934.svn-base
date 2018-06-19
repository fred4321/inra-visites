/* Répartition des visites programmées par site /année */
select count( naturevm) AS  quantité,  naturevm AS nature FROM vm AS v  WHERE v.date >= DATE_SUB(NOW(),INTERVAL 1 YEAR) GROUP BY naturevm;
/* Répartition des visites programmées par site /année */
select count( naturevm) AS  quantité,  naturevm AS nature FROM vm AS v  WHERE v.date >= DATE_SUB(NOW(),INTERVAL 1 YEAR) AND (v.statut = 'effectué' OR v.statut = 'cloturé' ) GROUP BY naturevm;
/* REPARTITION DES VISITES PAR SITE */
SELECT COUNT(*) AS nbVisite, site FROM vm AS v GROUP BY site;
/* REPARTITION DES VISITES PAR NATURE */
SELECT COUNT(*) AS nbVisite, naturevm AS nature FROM vm AS v GROUP BY naturevm;
/* REPARTITION PAR TYPES DE VISITES */
SELECT COUNT(*) AS nbVisite, v.site, u.nom, v.naturevm FROM vm  AS v  INNER JOIN unite AS u  ON v.unite_id = u.id GROUP BY v.naturevm, v.site, v.unite_id;
