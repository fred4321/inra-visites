SET SQL_SAFE_UPDATES = 0;
update agent as a set naturevm='VE' where naturevm = 'VPA' and 
(select count(*) from vm where agent_id = a.id)=0;
update agent as a set naturevm='VPA' where naturevm = 'VE' and 
(select count(*) from vm where agent_id = a.id)>0;
SET SQL_SAFE_UPDATES = 1;