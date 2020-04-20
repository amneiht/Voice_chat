use chat ;
DELIMITER $$
drop procedure if exists chat.addMember $$
CREATE PROCEDURE  chat.addMember( gp varchar(35) , admin varchar(35), mem varchar(35))
BEGIN
DECLARE pri int(2) ;
SELECT quyen into pri FROM tvNhom WHERE idNhom = gp and idTV = admin ;
if ( pri = 1 ) THEN
INSERT INTO tvNhom VALUES ( gp , mem , admin , 0 ) ;
end if ;
end $$

drop procedure if exists chat.deleteMember $$
CREATE PROCEDURE  chat.deleteMember( gp varchar(35) , admin varchar(35), mem varchar(35))
BEGIN
DECLARE pri int(2) ;
SELECT quyen into pri FROM tvNhom WHERE idNhom = gp and idTV = admin ;
if ( pri = 1 ) THEN
delete from  tvNhom where tvNhom.idNhom = gp and tvNhom.idTV = mem ;
end if ;
end $$

drop procedure if exists chat.addPri $$
CREATE PROCEDURE  chat.addPri( gp varchar(35) , admin varchar(35), mem varchar(35) , dt int(2) )
BEGIN
DECLARE pri int(2) ;
SELECT quyen into pri FROM tvNhom WHERE idNhom = gp and idTV = admin ;
if ( pri = 1 ) THEN
update tvNhom set quyen = dt where idNhom = gp and idTV = mem ;
end if ;
end $$

drop procedure if exists chat.deleteGroup $$
CREATE PROCEDURE  chat.deleteGroup( gp varchar(35) , admin varchar(35))
BEGIN
DECLARE pri int(2) ;
SELECT quyen into pri FROM tvNhom WHERE idNhom = gp and idTV = admin ;
if ( pri = 1 ) THEN
delete from tinNhan where idNhan = gp ;
delete from tvNhom where idNhom = gp ;
delete from nhom where nhom.idNhom = gp ;
end if ;
end $$
DELIMITER ;
