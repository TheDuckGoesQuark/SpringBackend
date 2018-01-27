DROP TABLE IF EXISTS `has_privilege`;
DROP TABLE IF EXISTS `involved_in`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `privilege`;
DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `file`;

CREATE TABLE IF NOT EXISTS `user` (
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(300) NOT NULL,
  `email` VARCHAR(320) NOT NULL,
  PRIMARY KEY (`username`));

CREATE TABLE IF NOT EXISTS `privilege` (
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `internal` BOOL NOT NULL,
  PRIMARY KEY (`name`));

INSERT INTO privilege (name, description, internal) VALUES
  ("admin", "can do everything", true),
  ("user", "can do some stuff", false);

CREATE TABLE IF NOT EXISTS `has_privilege` (
  `username` VARCHAR(100) NOT NULL,
  `privilege_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`, `privilege_name`),
  INDEX `privilege_idx` (`privilege_name` ASC),
  CONSTRAINT `user`
  FOREIGN KEY (`username`)
  REFERENCES `user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `privilege`
  FOREIGN KEY (`privilege_name`)
  REFERENCES `privilege` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `file` (
  `file_id` INT UNSIGNED NOT NULL,
  `path` VARCHAR(200) NOT NULL,
  `file_name` VARCHAR(54) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `meta-data` VARCHAR(45) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`file_id`));

CREATE TABLE IF NOT EXISTS `project` (
  `name` VARCHAR(100) NOT NULL,
  `root_dir_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `root_dir_id_UNIQUE` (`root_dir_id` ASC),
  CONSTRAINT `root_dir_id`
  FOREIGN KEY (`root_dir_id`)
  REFERENCES `file` (`file_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


CREATE TABLE IF NOT EXISTS `involved_in` (
  `username` VARCHAR(100) NOT NULL,
  `project_name` VARCHAR(100) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`, `project_name`),
  INDEX `project_name_idx` (`project_name` ASC),
  CONSTRAINT `involved_user`
  FOREIGN KEY (`username`)
  REFERENCES `user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `involved_project`
  FOREIGN KEY (`project_name`)
  REFERENCES `project` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


insert into user (username, email, password) values ('nkirkpatrick0', 'bdouche0@naver.com', 'H0v365');
insert into user (username, email, password) values ('fthirkettle1', 'uguage1@mayoclinic.com', 'ywZryK');
insert into user (username, email, password) values ('dpirrie2', 'calenshev2@t.co', '9YSBo1');
insert into user (username, email, password) values ('aswaine3', 'rbirkwood3@paginegialle.it', 'Gk9hffCi8bwX');
insert into user (username, email, password) values ('nrivaland4', 'htowersey4@mysql.com', 'nieEluOqL');
insert into user (username, email, password) values ('qtrever5', 'cbeetles5@amazonaws.com', 'MJjB1j');
insert into user (username, email, password) values ('jlongridge6', 'evannuchi6@pinterest.com', 'PnOo1mffvGRV');
insert into user (username, email, password) values ('hshingfield7', 'mwoolcocks7@ihg.com', 'eWFEb60me');
insert into user (username, email, password) values ('mfilipchikov8', 'agaydon8@google.co.uk', 'MikltoUpJJx6');
insert into user (username, email, password) values ('hstadding9', 'calexsandrev9@amazon.co.jp', 'XmeHdg8gk0Pm');
insert into user (username, email, password) values ('efrietta', 'sthurgooda@a8.net', 'gHaXyi');
insert into user (username, email, password) values ('olandreb', 'kdenidgeb@ihg.com', 'AhjDgJkYbx');
insert into user (username, email, password) values ('emcgeeverc', 'dswafferc@ed.gov', 'FUslljYY6off');
insert into user (username, email, password) values ('tmosed', 'gserfatid@dyndns.org', 'DIWQFDlaHz');
insert into user (username, email, password) values ('pgreschike', 'bfilisove@sfgate.com', 'PPlCIcSD7D');
insert into user (username, email, password) values ('kdeernessf', 'lbosdenf@google.es', 's6ZFYvWRL');
insert into user (username, email, password) values ('mpatienceg', 'crichemondg@buzzfeed.com', 'i2nEsf1L');
insert into user (username, email, password) values ('dharkush', 'rchalliceh@shutterfly.com', 'yCf7GqW');
insert into user (username, email, password) values ('nheddyi', 'wwoodgatei@51.la', 'BIbWGkye8mK');
insert into user (username, email, password) values ('ocutmerej', 'fguilloneauj@mysql.com', 'Glj0pA0ThXbM');
insert into user (username, email, password) values ('skillockk', 'kjurczikk@patch.com', 'DUBcYYtiucd');
insert into user (username, email, password) values ('lfendlenl', 'pthowl@cam.ac.uk', 'Yu99YQcwr1');
insert into user (username, email, password) values ('moffinm', 'smorecombm@desdev.cn', 'wm70kOXH');
insert into user (username, email, password) values ('kjacquotn', 'cgambelln@engadget.com', 'qah2sQQb');
insert into user (username, email, password) values ('oschiroko', 'cmccolgano@wired.com', 'Bv6sVhWmL');
insert into user (username, email, password) values ('emaudettp', 'edeangelisp@fastcompany.com', 'VSGCHjy2');
insert into user (username, email, password) values ('sgudgeonq', 'vluckeyq@foxnews.com', 'FvOs7uhlT3');
insert into user (username, email, password) values ('ggeorgiusr', 'astuddersr@stumbleupon.com', '7uRqAkQL');
insert into user (username, email, password) values ('emccarts', 'jgirardinis@a8.net', '0f46ZeB7Y');
insert into user (username, email, password) values ('tlendremt', 'pcrutcht@globo.com', 'B8lEx1iq');
insert into user (username, email, password) values ('sglossopu', 'dderricoatu@reddit.com', 'u1Dvgo');
insert into user (username, email, password) values ('jpachmannv', 'lcoxwellv@dropbox.com', 'ugsgZCCEvrk2');
insert into user (username, email, password) values ('wpierucciw', 'aitscowicsw@odnoklassniki.ru', '3wqX5PP1');
insert into user (username, email, password) values ('hdulieux', 'rcossellx@spotify.com', 'Eeew06s8vvXC');
insert into user (username, email, password) values ('kfortyy', 'dgoubliery@dedecms.com', '4fypTj5Vh9');
insert into user (username, email, password) values ('lmooneyz', 'rgarkenz@discuz.net', 'subEjl0y');
insert into user (username, email, password) values ('aswindon10', 'jdukesbury10@baidu.com', 'wulOOPQ');
insert into user (username, email, password) values ('dcallaway11', 'kredborn11@deliciousdays.com', 'my2JMGS14L3v');
insert into user (username, email, password) values ('ksabine12', 'pfranzonetti12@mediafire.com', 'BC6O7f6X6Pq');
insert into user (username, email, password) values ('dmacduff13', 'xlamar13@360.cn', 'kftb3za4X');
insert into user (username, email, password) values ('eviste14', 'tmachen14@360.cn', 'b3nBTO8tugCa');
insert into user (username, email, password) values ('btrinkwon15', 'bguerreau15@globo.com', 'xceUDfTd');
insert into user (username, email, password) values ('acrummay16', 'filyasov16@timesonline.co.uk', '4YArNrkhTM');
insert into user (username, email, password) values ('bbrun17', 'vmandrier17@admin.ch', '1bsKkU');
insert into user (username, email, password) values ('tehlerding18', 'ateal18@merriam-webster.com', 'kSFp7jFiXWY');
insert into user (username, email, password) values ('wyukhtin19', 'gyitzovitz19@reference.com', 'MTBccVTL');
insert into user (username, email, password) values ('rfenelow1a', 'ovanderdaal1a@cnbc.com', 'XTv84sIEFH2');
insert into user (username, email, password) values ('jcoppens1b', 'rhaslock1b@squarespace.com', '3QmL32yztxZZ');
insert into user (username, email, password) values ('dmcnutt1c', 'cmattessen1c@elegantthemes.com', 'nqltJwIXC');
insert into user (username, email, password) values ('ndeclairmont1d', 'ehabbergham1d@google.nl', 'ZU3F3Po9wM4');
insert into user (username, email, password) values ('jdradey1e', 'eortega1e@tuttocitta.it', 'DYhVg8');
insert into user (username, email, password) values ('ghadland1f', 'jdifranceshci1f@usda.gov', 'sSe2txcC90');
insert into user (username, email, password) values ('gmclurg1g', 'avanderhoeven1g@tamu.edu', 'gLtRa0vtvjI');
insert into user (username, email, password) values ('chowick1h', 'spickard1h@accuweather.com', 'riAYaPha229C');
insert into user (username, email, password) values ('wpiper1i', 'lrymell1i@homestead.com', 'CEkyVrU0cM');
insert into user (username, email, password) values ('mwison1j', 'eredsall1j@dagondesign.com', 'IDLWSIQ');
insert into user (username, email, password) values ('tpellamonuten1k', 'knelissen1k@ehow.com', 'TboTcVP0qjol');
insert into user (username, email, password) values ('kreveland1l', 'zsulland1l@msu.edu', 'zKlM3A9i');
insert into user (username, email, password) values ('mczyz1m', 'smckelvie1m@constantcontact.com', 'zZN56fH5eB');
insert into user (username, email, password) values ('lbardwall1n', 'jwinscomb1n@phoca.cz', 'et48Y7');
insert into user (username, email, password) values ('gollerhead1o', 'kgardener1o@wordpress.org', 'HVQc5DXwCprT');
insert into user (username, email, password) values ('jkivlehan1p', 'jhanbury1p@simplemachines.org', 'z33hHoblRgsW');
insert into user (username, email, password) values ('iberceros1q', 'bgrzelewski1q@sciencedirect.com', 'Gq7rE76xxuR');
insert into user (username, email, password) values ('hmillery1r', 'rriggott1r@geocities.jp', 'BKeA3RfVIr');
insert into user (username, email, password) values ('hcodlin1s', 'ovarvara1s@sun.com', 'C6OIlgpm');
insert into user (username, email, password) values ('lkeynes1t', 'atroillet1t@java.com', 'Wn8rGlaG');
insert into user (username, email, password) values ('jphilips1u', 'shallbird1u@ucla.edu', 'GahFhW8');
insert into user (username, email, password) values ('jmason1v', 'gbanasik1v@tumblr.com', 'MqyRvjl2V');
insert into user (username, email, password) values ('fmein1w', 'rconnett1w@unblog.fr', 'VuzVJJrnK1ss');
insert into user (username, email, password) values ('rhands1x', 'jibbitson1x@studiopress.com', 'AtcbNwsr4gCy');
insert into user (username, email, password) values ('efowley1y', 'lmapplebeck1y@auda.org.au', 'QP1rSkZ6');
insert into user (username, email, password) values ('dbrennand1z', 'lthomazin1z@state.gov', 'ZOaDCNQOY8eH');
insert into user (username, email, password) values ('ralmon20', 'lsudell20@ebay.com', 'shltFZ');
insert into user (username, email, password) values ('dhumbatch21', 'pside21@squidoo.com', '1lkOv3b08b');
insert into user (username, email, password) values ('bmacwhirter22', 'amaccolm22@jigsy.com', 'OW57e7hHx');
insert into user (username, email, password) values ('kcannavan23', 'agergler23@narod.ru', 'PfBIIvH5');
insert into user (username, email, password) values ('kthwaite24', 'abroke24@latimes.com', 'piIXGH');
insert into user (username, email, password) values ('smableson25', 'rgrindell25@whitehouse.gov', 'fVb5rLZuiSM');
insert into user (username, email, password) values ('sreina26', 'kelsmore26@qq.com', 'er99gmqa4cDQ');
insert into user (username, email, password) values ('bmoors27', 'msearchfield27@newsvine.com', 'ckEeK52UY');
insert into user (username, email, password) values ('apavinese28', 'mduignan28@nyu.edu', 'HfGy2PyqeYw5');
insert into user (username, email, password) values ('pcuddy29', 'gbreawood29@ucoz.ru', 'OMlUfHInhW');
insert into user (username, email, password) values ('brobrose2a', 'tcastle2a@comcast.net', 'yV2i7bAvC');
insert into user (username, email, password) values ('hhenryson2b', 'edumblton2b@rambler.ru', 'pBRv8On64Rd');
insert into user (username, email, password) values ('ksouthon2c', 'rjosiah2c@java.com', 'ORgEihaaB00Z');
insert into user (username, email, password) values ('lterney2d', 'sdawltrey2d@home.pl', 'rn8x4Najz');
insert into user (username, email, password) values ('kroback2e', 'rphizackarley2e@pcworld.com', '5eIBGgMme');
insert into user (username, email, password) values ('gsapena2f', 'tarnefield2f@arizona.edu', 'WMZUS1A');
insert into user (username, email, password) values ('eblincow2g', 'jbockler2g@ow.ly', 'uB6hTR');
insert into user (username, email, password) values ('csansum2h', 'jjurick2h@wordpress.com', 'RO1P92');
insert into user (username, email, password) values ('nmccromley2i', 'mdevey2i@myspace.com', 'mgUnPow');
insert into user (username, email, password) values ('ifitzgilbert2j', 'dgrassett2j@amazon.co.uk', 'X3Qwxe');
insert into user (username, email, password) values ('fscad2k', 'rstoyles2k@gravatar.com', '5AlNtqgEI6');
insert into user (username, email, password) values ('bmattholie2l', 'bdelasalle2l@zimbio.com', 'DQBQKBmS');
insert into user (username, email, password) values ('bhowieson2m', 'lrogge2m@wp.com', 'sV1urrPAB');
insert into user (username, email, password) values ('nseagrave2n', 'mfallon2n@google.ru', 'Bk8iUbHhW');
insert into user (username, email, password) values ('btruce2o', 'rwiltshear2o@over-blog.com', 'oIgzbMsT1Ofp');
insert into user (username, email, password) values ('hvedeniktov2p', 'sdayer2p@buzzfeed.com', 'Fg4PNeO');
insert into user (username, email, password) values ('jricarde2q', 'rlethlay2q@oracle.com', 'jlHmfTL');
insert into user (username, email, password) values ('apipkin2r', 'dcorbishley2r@g.co', 'SzXu0QO');
insert into user (username, email, password) values ('ggaymer2s', 'gromushkin2s@bloomberg.com', 'mC65Cu2R');
insert into user (username, email, password) values ('tferonet2t', 'emargetson2t@ezinearticles.com', 'xqg0TME2');
insert into user (username, email, password) values ('shouliston2u', 'bnielson2u@netlog.com', 'LJvErab');
insert into user (username, email, password) values ('ewhittles2v', 'tfaccini2v@cdc.gov', '8bhFcYkxZ');
insert into user (username, email, password) values ('tgrinishin2w', 'vdenisovich2w@china.com.cn', '4YKIlsG4gryH');
insert into user (username, email, password) values ('jgreetland2x', 'bgrouvel2x@opera.com', 'arWVKle6opY');
insert into user (username, email, password) values ('pizzett2y', 'gmayze2y@tamu.edu', '9SxzteI5JyT4');
insert into user (username, email, password) values ('svandenbroek2z', 'pdicarli2z@nhs.uk', 'BHCrGNO');
insert into user (username, email, password) values ('nattrey30', 'spavyer30@php.net', 'pM9kujwtj');
insert into user (username, email, password) values ('mpetticrew31', 'fjuan31@google.nl', 'kgjYlW0B');
insert into user (username, email, password) values ('akropach32', 'priddle32@about.me', 'Ogy2VWGGDVd');
insert into user (username, email, password) values ('ilattos33', 'terickssen33@exblog.jp', 'IhKCSDPaJHHS');
insert into user (username, email, password) values ('etrevains34', 'tbezemer34@xrea.com', 'j97qu0G8gY10');
insert into user (username, email, password) values ('bkeysall35', 'raisbett35@clickbank.net', 'huYD0F68bW');
insert into user (username, email, password) values ('ltoohey36', 'astorms36@tinyurl.com', 'iWuj7Rvdx');
insert into user (username, email, password) values ('dcowtherd37', 'bnusche37@shutterfly.com', '3Jf6qh');
insert into user (username, email, password) values ('fleel38', 'aneaverson38@cdbaby.com', 'QukC86');
insert into user (username, email, password) values ('mroarty39', 'mivanyushin39@omniture.com', 'QV68Mlj2bKUt');
insert into user (username, email, password) values ('dlayzell3a', 'ljoris3a@ed.gov', 'AUZs1a8');
insert into user (username, email, password) values ('hquarmby3b', 'ckepe3b@blogs.com', 'hs4BKHsM0');
insert into user (username, email, password) values ('cwebermann3c', 'mchalfain3c@imdb.com', 'OFEh1wI');
insert into user (username, email, password) values ('jmarkos3d', 'dpeartree3d@csmonitor.com', '58TBuQS');
insert into user (username, email, password) values ('tenstone3e', 'cmaas3e@webmd.com', '7iA1TRqo8I');
insert into user (username, email, password) values ('rjeannequin3f', 'chuish3f@washingtonpost.com', '73YNkguw');
insert into user (username, email, password) values ('dlodwig3g', 'rkruszelnicki3g@google.de', 'bAC33YkMd');
insert into user (username, email, password) values ('glackney3h', 'ascotchmur3h@jimdo.com', 'LIepkhaPPM');
insert into user (username, email, password) values ('nnoto3i', 'tmacellen3i@imageshack.us', 'McgzANp1Eby');
insert into user (username, email, password) values ('nllorens3j', 'abrychan3j@ibm.com', 'KqzltKWYcJH');
insert into user (username, email, password) values ('emansford3k', 'lshirrell3k@technorati.com', 'xv7ItUarob');
insert into user (username, email, password) values ('swastie3l', 'sgallehawk3l@g.co', 'FA3Bh1V4aq');
insert into user (username, email, password) values ('hshambrook3m', 'ahaliday3m@1688.com', 'LamlClmyXFV');
insert into user (username, email, password) values ('jgaroghan3n', 'bgorthy3n@cargocollective.com', 'vV7s8HZkFV');
insert into user (username, email, password) values ('nboom3o', 'bmccuaig3o@webmd.com', 'ESJDkdue');
insert into user (username, email, password) values ('bdinan3p', 'dmussolini3p@github.io', 'L4UXHXY6B');
insert into user (username, email, password) values ('cmidlane3q', 'jkneesha3q@ox.ac.uk', 'IsCNfdBIkc9L');
insert into user (username, email, password) values ('jdukesbury3r', 'brechert3r@google.com.br', 'Yw9YyjjbwL');
insert into user (username, email, password) values ('atomblings3s', 'lbremner3s@wiley.com', 'drQccY');
insert into user (username, email, password) values ('mrawlison3t', 'jmclanaghan3t@hubpages.com', 'd3xglu8iIF');
insert into user (username, email, password) values ('kohdirscoll3u', 'svillage3u@instagram.com', 'uPENdQ');
insert into user (username, email, password) values ('gbeavon3v', 'abinder3v@yahoo.com', 'q6oDmah');
insert into user (username, email, password) values ('vmcreynold3w', 'brous3w@huffingtonpost.com', 'U6tHLGs7OexA');
insert into user (username, email, password) values ('aalliker3x', 'taudus3x@diigo.com', 'uOq6oVHla3i');
insert into user (username, email, password) values ('mbloodworth3y', 'ddowty3y@homestead.com', '9W8lGhiG7hwa');
insert into user (username, email, password) values ('rdomerque3z', 'rmaccumeskey3z@uiuc.edu', '7FeoKYT6eDLj');
insert into user (username, email, password) values ('kparry40', 'lorganer40@engadget.com', 'GNuNU9pFqcsq');
insert into user (username, email, password) values ('cbaudone41', 'cmaster41@walmart.com', 'BG7oi03RjzK3');
insert into user (username, email, password) values ('drodge42', 'mpozer42@tinyurl.com', 'Q9Cl2t');
insert into user (username, email, password) values ('jklaessen43', 'fetuck43@accuweather.com', 'Tg7BVZr');
insert into user (username, email, password) values ('bbenger44', 'jspours44@constantcontact.com', 'izLvkPUiu1a');
insert into user (username, email, password) values ('spynn45', 'shockell45@gravatar.com', 'KDpTOyGijnt');
insert into user (username, email, password) values ('gonoulane46', 'nbiskupiak46@cbslocal.com', 'IHmrLx');
insert into user (username, email, password) values ('mmalia47', 'fashburner47@china.com.cn', 'aUGsmA');
insert into user (username, email, password) values ('jszimoni48', 'wfishby48@ning.com', '10L9nzJwiV');
insert into user (username, email, password) values ('tcomley49', 'tfoakes49@jugem.jp', 'KNJDiSLv');
insert into user (username, email, password) values ('lmasser4a', 'hmaclure4a@tripod.com', 'RmIgfLY9ScO');
insert into user (username, email, password) values ('whasely4b', 'awalkden4b@narod.ru', 'SmKMtGRk');
insert into user (username, email, password) values ('ochallen4c', 'ebienvenu4c@amazon.co.uk', 'wB8bNd');
insert into user (username, email, password) values ('lmcavin4d', 'phasson4d@opera.com', 'rT6Mjd');
insert into user (username, email, password) values ('dtirrey4e', 'dbrisset4e@alexa.com', 'mafNXq');
insert into user (username, email, password) values ('rmingo4f', 'kclemo4f@deliciousdays.com', 'EBFZhYYLDY');
insert into user (username, email, password) values ('aredpath4g', 'mgilli4g@reddit.com', 'eBPzIzGxYPBd');
insert into user (username, email, password) values ('apagen4h', 'hbertenshaw4h@ibm.com', 'UbN0AFAj');
insert into user (username, email, password) values ('rfley4i', 'nlotherington4i@abc.net.au', '1Y7r8N');
insert into user (username, email, password) values ('epadilla4j', 'cboreham4j@samsung.com', 'dTuT4GCKTB');
insert into user (username, email, password) values ('imarrion4k', 'pgerriets4k@nytimes.com', 'NyezvYtps');
insert into user (username, email, password) values ('ahenkmann4l', 'khullock4l@networkadvertising.org', 'O4etsGk');
insert into user (username, email, password) values ('ubenner4m', 'ascutcheon4m@wiley.com', '8MU8Mn4');
insert into user (username, email, password) values ('jelwin4n', 'jcreyke4n@sciencedaily.com', 'Z8XzgO4');
insert into user (username, email, password) values ('mpurdey4o', 'cgarmey4o@macromedia.com', 'JWL1wzn92qM');
insert into user (username, email, password) values ('mbraycotton4p', 'omiles4p@bloglines.com', 'seCGji5p');
insert into user (username, email, password) values ('ldinsell4q', 'lfeveryear4q@unesco.org', 'dwHkRGNJ');
insert into user (username, email, password) values ('cmussolini4r', 'polivey4r@google.it', '3GBEEs1');
insert into user (username, email, password) values ('sskelton4s', 'ljadczak4s@whitehouse.gov', 'Ks5RnV8i0ul');
insert into user (username, email, password) values ('varnett4t', 'aworthy4t@dot.gov', '24GxElHeR4');
insert into user (username, email, password) values ('atarpey4u', 'dweich4u@gov.uk', 'A5MYbbQcoMw');
insert into user (username, email, password) values ('mhallwell4v', 'dwaymont4v@wufoo.com', '4cj7aJgwC');
insert into user (username, email, password) values ('rwilkinson4w', 'bkirtlan4w@fema.gov', 'xgmR7PM80');
insert into user (username, email, password) values ('echalcraft4x', 'neadon4x@amazon.co.uk', 'O9IGDhSpJ6QG');
insert into user (username, email, password) values ('bbeisley4y', 'rmacilory4y@shinystat.com', 'koN9xy0Rcj');
insert into user (username, email, password) values ('mfirmin4z', 'egaspard4z@spotify.com', 'ZCZDs0P1nuyW');
insert into user (username, email, password) values ('ciapico50', 'awatsonbrown50@ning.com', 'CQ2HNrs8M');
insert into user (username, email, password) values ('amcboyle51', 'lstuckes51@chronoengine.com', '4Uv7bAVCz9');
insert into user (username, email, password) values ('gjanak52', 'bolennachain52@desdev.cn', 'cobNAttj1');
insert into user (username, email, password) values ('lnawton53', 'cmacdunlevy53@microsoft.com', 'Hp639J');
insert into user (username, email, password) values ('kpannaman54', 'njosling54@nsw.gov.au', 'Scqey3AyD');
insert into user (username, email, password) values ('dtourle55', 'hcadney55@reference.com', 'gy23ini');
insert into user (username, email, password) values ('cedwardson56', 'cbeverage56@cbsnews.com', 'eMDb0l1jaNy7');
insert into user (username, email, password) values ('cnorquay57', 'bthow57@alexa.com', 'PkjY2GYb49');
insert into user (username, email, password) values ('jcundey58', 'agrabiec58@cocolog-nifty.com', '1GCeN6J');
insert into user (username, email, password) values ('bsmooth59', 'gmanktelow59@weebly.com', 'gKHOjSdfG');
insert into user (username, email, password) values ('rshuter5a', 'bdybell5a@gov.uk', '9pyrg7q');
insert into user (username, email, password) values ('cbowgen5b', 'ffife5b@marketwatch.com', 'n9UElcjK');
insert into user (username, email, password) values ('bantonik5c', 'eshafe5c@newsvine.com', 'xzrLRSSwKU');
insert into user (username, email, password) values ('pfleckno5d', 'lpasley5d@bloomberg.com', 'WajD6cb');
insert into user (username, email, password) values ('rkobu5e', 'bveryan5e@sina.com.cn', 'bu9BxElSE5kk');
insert into user (username, email, password) values ('dkunze5f', 'tnix5f@mail.ru', 'MMXRUYYUT');
insert into user (username, email, password) values ('triccardini5g', 'rmcveigh5g@csmonitor.com', 'ttzfNeucP1');
insert into user (username, email, password) values ('nclows5h', 'wwedderburn5h@sbwire.com', 'nQ6mV35');
insert into user (username, email, password) values ('gonyon5i', 'aanselmi5i@bloomberg.com', 'KRONoZJKgX');
insert into user (username, email, password) values ('tconnow5j', 'gmcdonogh5j@t.co', 'jwTQEIn2');



insert into has_privilege (username,privilege_name) values ('nkirkpatrick0', 'admin');
insert into has_privilege (username,privilege_name) values ('fthirkettle1', 'user');
insert into has_privilege (username,privilege_name) values ('dpirrie2', 'admin');
insert into has_privilege (username,privilege_name) values ('aswaine3', 'admin');
insert into has_privilege (username,privilege_name) values ('nrivaland4', 'user');
insert into has_privilege (username,privilege_name) values ('qtrever5', 'user');
insert into has_privilege (username,privilege_name) values ('jlongridge6', 'user');
insert into has_privilege (username,privilege_name) values ('hshingfield7', 'admin');
insert into has_privilege (username,privilege_name) values ('mfilipchikov8', 'user');
insert into has_privilege (username,privilege_name) values ('hstadding9', 'user');
insert into has_privilege (username,privilege_name) values ('efrietta', 'user');
insert into has_privilege (username,privilege_name) values ('olandreb', 'user');
insert into has_privilege (username,privilege_name) values ('emcgeeverc', 'user');
insert into has_privilege (username,privilege_name) values ('tmosed', 'user');
insert into has_privilege (username,privilege_name) values ('pgreschike', 'user');
insert into has_privilege (username,privilege_name) values ('kdeernessf', 'user');
insert into has_privilege (username,privilege_name) values ('mpatienceg', 'user');
insert into has_privilege (username,privilege_name) values ('dharkush', 'user');
insert into has_privilege (username,privilege_name) values ('nheddyi', 'admin');
insert into has_privilege (username,privilege_name) values ('ocutmerej', 'user');
insert into has_privilege (username,privilege_name) values ('skillockk', 'user');
insert into has_privilege (username,privilege_name) values ('lfendlenl', 'admin');
insert into has_privilege (username,privilege_name) values ('moffinm', 'admin');
insert into has_privilege (username,privilege_name) values ('kjacquotn', 'admin');
insert into has_privilege (username,privilege_name) values ('oschiroko', 'user');
insert into has_privilege (username,privilege_name) values ('emaudettp', 'user');
insert into has_privilege (username,privilege_name) values ('sgudgeonq', 'user');
insert into has_privilege (username,privilege_name) values ('ggeorgiusr', 'user');
insert into has_privilege (username,privilege_name) values ('emccarts', 'user');
insert into has_privilege (username,privilege_name) values ('tlendremt', 'user');
insert into has_privilege (username,privilege_name) values ('sglossopu', 'user');
insert into has_privilege (username,privilege_name) values ('jpachmannv', 'user');
insert into has_privilege (username,privilege_name) values ('wpierucciw', 'admin');
insert into has_privilege (username,privilege_name) values ('hdulieux', 'user');
insert into has_privilege (username,privilege_name) values ('kfortyy', 'user');
insert into has_privilege (username,privilege_name) values ('lmooneyz', 'user');
insert into has_privilege (username,privilege_name) values ('aswindon10', 'admin');
insert into has_privilege (username,privilege_name) values ('dcallaway11', 'user');
insert into has_privilege (username,privilege_name) values ('ksabine12', 'user');
insert into has_privilege (username,privilege_name) values ('dmacduff13', 'user');
insert into has_privilege (username,privilege_name) values ('eviste14', 'user');
insert into has_privilege (username,privilege_name) values ('btrinkwon15', 'user');
insert into has_privilege (username,privilege_name) values ('acrummay16', 'admin');
insert into has_privilege (username,privilege_name) values ('bbrun17', 'admin');
insert into has_privilege (username,privilege_name) values ('tehlerding18', 'user');
insert into has_privilege (username,privilege_name) values ('wyukhtin19', 'user');
insert into has_privilege (username,privilege_name) values ('rfenelow1a', 'admin');
insert into has_privilege (username,privilege_name) values ('jcoppens1b', 'admin');
insert into has_privilege (username,privilege_name) values ('dmcnutt1c', 'admin');
insert into has_privilege (username,privilege_name) values ('ndeclairmont1d', 'user');
insert into has_privilege (username,privilege_name) values ('jdradey1e', 'user');
insert into has_privilege (username,privilege_name) values ('ghadland1f', 'admin');
insert into has_privilege (username,privilege_name) values ('gmclurg1g', 'admin');
insert into has_privilege (username,privilege_name) values ('chowick1h', 'user');
insert into has_privilege (username,privilege_name) values ('wpiper1i', 'admin');
insert into has_privilege (username,privilege_name) values ('mwison1j', 'user');
insert into has_privilege (username,privilege_name) values ('tpellamonuten1k', 'admin');
insert into has_privilege (username,privilege_name) values ('kreveland1l', 'admin');
insert into has_privilege (username,privilege_name) values ('mczyz1m', 'admin');
insert into has_privilege (username,privilege_name) values ('lbardwall1n', 'admin');
insert into has_privilege (username,privilege_name) values ('gollerhead1o', 'user');
insert into has_privilege (username,privilege_name) values ('jkivlehan1p', 'user');
insert into has_privilege (username,privilege_name) values ('iberceros1q', 'admin');
insert into has_privilege (username,privilege_name) values ('hmillery1r', 'user');
insert into has_privilege (username,privilege_name) values ('hcodlin1s', 'user');
insert into has_privilege (username,privilege_name) values ('lkeynes1t', 'user');
insert into has_privilege (username,privilege_name) values ('jphilips1u', 'user');
insert into has_privilege (username,privilege_name) values ('jmason1v', 'admin');
insert into has_privilege (username,privilege_name) values ('fmein1w', 'user');
insert into has_privilege (username,privilege_name) values ('rhands1x', 'user');
insert into has_privilege (username,privilege_name) values ('efowley1y', 'user');
insert into has_privilege (username,privilege_name) values ('dbrennand1z', 'user');
insert into has_privilege (username,privilege_name) values ('ralmon20', 'user');
insert into has_privilege (username,privilege_name) values ('dhumbatch21', 'user');
insert into has_privilege (username,privilege_name) values ('bmacwhirter22', 'admin');
insert into has_privilege (username,privilege_name) values ('kcannavan23', 'user');
insert into has_privilege (username,privilege_name) values ('kthwaite24', 'user');
insert into has_privilege (username,privilege_name) values ('smableson25', 'admin');
insert into has_privilege (username,privilege_name) values ('sreina26', 'user');
insert into has_privilege (username,privilege_name) values ('bmoors27', 'user');
insert into has_privilege (username,privilege_name) values ('apavinese28', 'user');
insert into has_privilege (username,privilege_name) values ('pcuddy29', 'admin');
insert into has_privilege (username,privilege_name) values ('brobrose2a', 'user');
insert into has_privilege (username,privilege_name) values ('hhenryson2b', 'user');
insert into has_privilege (username,privilege_name) values ('ksouthon2c', 'user');
insert into has_privilege (username,privilege_name) values ('lterney2d', 'admin');
insert into has_privilege (username,privilege_name) values ('kroback2e', 'user');
insert into has_privilege (username,privilege_name) values ('gsapena2f', 'admin');
insert into has_privilege (username,privilege_name) values ('eblincow2g', 'admin');
insert into has_privilege (username,privilege_name) values ('csansum2h', 'admin');
insert into has_privilege (username,privilege_name) values ('nmccromley2i', 'user');
insert into has_privilege (username,privilege_name) values ('ifitzgilbert2j', 'admin');
insert into has_privilege (username,privilege_name) values ('fscad2k', 'user');
insert into has_privilege (username,privilege_name) values ('bmattholie2l', 'user');
insert into has_privilege (username,privilege_name) values ('bhowieson2m', 'admin');
insert into has_privilege (username,privilege_name) values ('nseagrave2n', 'user');
insert into has_privilege (username,privilege_name) values ('btruce2o', 'user');
insert into has_privilege (username,privilege_name) values ('hvedeniktov2p', 'user');
insert into has_privilege (username,privilege_name) values ('jricarde2q', 'admin');
insert into has_privilege (username,privilege_name) values ('apipkin2r', 'admin');
insert into has_privilege (username,privilege_name) values ('aalliker3x', 'user');
insert into has_privilege (username,privilege_name) values ('ahenkmann4l', 'user');
insert into has_privilege (username,privilege_name) values ('akropach32', 'user');
insert into has_privilege (username,privilege_name) values ('amcboyle51', 'user');
insert into has_privilege (username,privilege_name) values ('apagen4h', 'user');
insert into has_privilege (username,privilege_name) values ('aredpath4g', 'user');
insert into has_privilege (username,privilege_name) values ('atarpey4u', 'user');
insert into has_privilege (username,privilege_name) values ('atomblings3s', 'user');
insert into has_privilege (username,privilege_name) values ('bantonik5c', 'user');
insert into has_privilege (username,privilege_name) values ('bbeisley4y', 'user');
insert into has_privilege (username,privilege_name) values ('bbenger44', 'user');
insert into has_privilege (username,privilege_name) values ('bdinan3p', 'user');
insert into has_privilege (username,privilege_name) values ('bkeysall35', 'user');
insert into has_privilege (username,privilege_name) values ('bsmooth59', 'user');
insert into has_privilege (username,privilege_name) values ('cbaudone41', 'user');
insert into has_privilege (username,privilege_name) values ('cbowgen5b', 'user');
insert into has_privilege (username,privilege_name) values ('cedwardson56', 'user');
insert into has_privilege (username,privilege_name) values ('ciapico50', 'user');
insert into has_privilege (username,privilege_name) values ('cmidlane3q', 'user');
insert into has_privilege (username,privilege_name) values ('cmussolini4r', 'user');
insert into has_privilege (username,privilege_name) values ('cnorquay57', 'user');
insert into has_privilege (username,privilege_name) values ('cwebermann3c', 'user');
insert into has_privilege (username,privilege_name) values ('dcowtherd37', 'user');
insert into has_privilege (username,privilege_name) values ('dkunze5f', 'user');
insert into has_privilege (username,privilege_name) values ('dlayzell3a', 'user');
insert into has_privilege (username,privilege_name) values ('dlodwig3g', 'user');
insert into has_privilege (username,privilege_name) values ('drodge42', 'user');
insert into has_privilege (username,privilege_name) values ('dtirrey4e', 'user');
insert into has_privilege (username,privilege_name) values ('dtourle55', 'user');
insert into has_privilege (username,privilege_name) values ('echalcraft4x', 'user');
insert into has_privilege (username,privilege_name) values ('emansford3k', 'user');
insert into has_privilege (username,privilege_name) values ('epadilla4j', 'user');
insert into has_privilege (username,privilege_name) values ('etrevains34', 'user');
insert into has_privilege (username,privilege_name) values ('ewhittles2v', 'user');
insert into has_privilege (username,privilege_name) values ('fleel38', 'user');
insert into has_privilege (username,privilege_name) values ('gbeavon3v', 'user');
insert into has_privilege (username,privilege_name) values ('ggaymer2s', 'user');
insert into has_privilege (username,privilege_name) values ('gjanak52', 'user');
insert into has_privilege (username,privilege_name) values ('glackney3h', 'user');
insert into has_privilege (username,privilege_name) values ('gonoulane46', 'user');
insert into has_privilege (username,privilege_name) values ('gonyon5i', 'user');
insert into has_privilege (username,privilege_name) values ('hquarmby3b', 'user');
insert into has_privilege (username,privilege_name) values ('hshambrook3m', 'user');
insert into has_privilege (username,privilege_name) values ('ilattos33', 'user');
insert into has_privilege (username,privilege_name) values ('imarrion4k', 'user');
insert into has_privilege (username,privilege_name) values ('jcundey58', 'user');
insert into has_privilege (username,privilege_name) values ('jdukesbury3r', 'user');
insert into has_privilege (username,privilege_name) values ('jelwin4n', 'user');
insert into has_privilege (username,privilege_name) values ('jgaroghan3n', 'user');
insert into has_privilege (username,privilege_name) values ('jgreetland2x', 'user');
insert into has_privilege (username,privilege_name) values ('jklaessen43', 'user');
insert into has_privilege (username,privilege_name) values ('jmarkos3d', 'user');
insert into has_privilege (username,privilege_name) values ('jszimoni48', 'user');
insert into has_privilege (username,privilege_name) values ('kohdirscoll3u', 'user');
insert into has_privilege (username,privilege_name) values ('kpannaman54', 'user');
insert into has_privilege (username,privilege_name) values ('kparry40', 'user');
insert into has_privilege (username,privilege_name) values ('ldinsell4q', 'user');
insert into has_privilege (username,privilege_name) values ('lmasser4a', 'user');
insert into has_privilege (username,privilege_name) values ('lmcavin4d', 'user');
insert into has_privilege (username,privilege_name) values ('lnawton53', 'user');
insert into has_privilege (username,privilege_name) values ('ltoohey36', 'user');
insert into has_privilege (username,privilege_name) values ('mbloodworth3y', 'user');
insert into has_privilege (username,privilege_name) values ('mbraycotton4p', 'user');
insert into has_privilege (username,privilege_name) values ('mfirmin4z', 'user');
insert into has_privilege (username,privilege_name) values ('mhallwell4v', 'user');
insert into has_privilege (username,privilege_name) values ('mmalia47', 'user');
insert into has_privilege (username,privilege_name) values ('mpetticrew31', 'user');
insert into has_privilege (username,privilege_name) values ('mpurdey4o', 'user');
insert into has_privilege (username,privilege_name) values ('mrawlison3t', 'user');
insert into has_privilege (username,privilege_name) values ('mroarty39', 'user');
insert into has_privilege (username,privilege_name) values ('nattrey30', 'user');
insert into has_privilege (username,privilege_name) values ('nboom3o', 'user');
insert into has_privilege (username,privilege_name) values ('nclows5h', 'user');
insert into has_privilege (username,privilege_name) values ('nllorens3j', 'user');
insert into has_privilege (username,privilege_name) values ('nnoto3i', 'user');
insert into has_privilege (username,privilege_name) values ('ochallen4c', 'user');
insert into has_privilege (username,privilege_name) values ('pfleckno5d', 'user');
insert into has_privilege (username,privilege_name) values ('pizzett2y', 'user');
insert into has_privilege (username,privilege_name) values ('rdomerque3z', 'user');
insert into has_privilege (username,privilege_name) values ('rfley4i', 'user');
insert into has_privilege (username,privilege_name) values ('rjeannequin3f', 'user');
insert into has_privilege (username,privilege_name) values ('rkobu5e', 'user');
insert into has_privilege (username,privilege_name) values ('rmingo4f', 'user');
insert into has_privilege (username,privilege_name) values ('rshuter5a', 'user');
insert into has_privilege (username,privilege_name) values ('rwilkinson4w', 'user');
insert into has_privilege (username,privilege_name) values ('shouliston2u', 'user');
insert into has_privilege (username,privilege_name) values ('spynn45', 'user');
insert into has_privilege (username,privilege_name) values ('sskelton4s', 'user');
insert into has_privilege (username,privilege_name) values ('svandenbroek2z', 'user');
insert into has_privilege (username,privilege_name) values ('swastie3l', 'user');
insert into has_privilege (username,privilege_name) values ('tcomley49', 'user');
insert into has_privilege (username,privilege_name) values ('tconnow5j', 'user');
insert into has_privilege (username,privilege_name) values ('tenstone3e', 'user');
insert into has_privilege (username,privilege_name) values ('tferonet2t', 'user');
insert into has_privilege (username,privilege_name) values ('tgrinishin2w', 'user');
insert into has_privilege (username,privilege_name) values ('triccardini5g', 'user');
insert into has_privilege (username,privilege_name) values ('ubenner4m', 'user');
insert into has_privilege (username,privilege_name) values ('varnett4t', 'user');
insert into has_privilege (username,privilege_name) values ('vmcreynold3w', 'user');
insert into has_privilege (username,privilege_name) values ('whasely4b', 'user');