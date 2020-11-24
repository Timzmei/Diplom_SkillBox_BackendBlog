insert into user (email, is_moderator, name, password, reg_time) values('Yulya@yandex.ru', '1', 'Yulya', '$2a$12$pvrSSP/S7gb1Q/ORwiQvbu/ianHCOgpSMEje9DQOr8SEUTdaffKxW', '2020-09-15');
insert into user (email, is_moderator, name, password, reg_time) values('Masha@yandex.ru', '0', 'Masha', '$2a$12$pvrSSP/S7gb1Q/ORwiQvbu/ianHCOgpSMEje9DQOr8SEUTdaffKxW', '2020-09-12');
insert into post (is_active, moderation_status, text, time, title, view_count, moderator_id, user_id) values('1', 'ACCEPTED', 'OneNote — это программа от Microsoft, которая не просто позволяет создавать заметки, но и полностью заменяет бумажный блокнот. Если вы были пользователем ًWindows, и после миграции на Linux не хотите расставаться с привычной «записной книжкой», то для вас есть хорошие новости — это приложение легко устанавливается в большинстве популярных операционных систем.', '2020-09-26 17:20:31', 'АНАЛОГИ ONENOTE В LINUX', '1', '1', '1');
insert into post (is_active, moderation_status, text, time, title, view_count, moderator_id, user_id) values('1', 'ACCEPTED', 'Если на вашем жёстком диске закончилось свободное пространство, самый простой способ его освободить - найти и удалить самые большие и при этом ненужные файлы. Такая задача может возникнуть как на сервере, так и на домашнем компьютере, поэтому существуют удобные решения для обоих ситуаций. Способов поиска больших файлов существует очень много.', '2020-09-27 19:20:31', 'ПОИСК БОЛЬШИХ ФАЙЛОВ LINUX', '1', '1', '1');
insert into post (is_active, moderation_status, text, time, title, view_count, moderator_id, user_id) values('1', 'ACCEPTED', 'Если вы — пользователь Ubuntu 20.04 и вам нужен бесплатный пакет офисных программ, который разрешено использовать не только для личных нужд, но и в коммерческих целях, обратите внимание на FreeOffice', '2020-09-28 19:20:31', 'УСТАНОВКА FREEOFFICE В UBUNTU', '1', '1', '1');
insert into post (is_active, moderation_status, text, time, title, view_count, moderator_id, user_id) values('1', 'ACCEPTED', 'В последние годы очень большую популярность набрали флешки в качестве устройства для установки операционных систем. Но было бы очень неплохо иметь не одну операционную систему на флешке, а несколько. Скажем несколько разных дистрибутивов Linux, или даже флешку с Windows и Linux. Это возможно.', '2020-09-29 19:20:31', 'МУЛЬТИЗАГРУЗОЧНАЯ ФЛЕШКА С НЕСКОЛЬКИМИ ОС LINUX', '1', '1', '1');
insert into post (is_active, moderation_status, text, time, title, view_count, moderator_id, user_id) values('1', 'ACCEPTED', 'Недавно вышла новая версия дистрибутива Linux Mint 20, основанная на LTS выпуске Ubuntu 20.04. В этой новой версии обновлено окружение Cinnamon до 4.6, улучшен внешний вид, улучшена поддержка Nvidia Optimus, добавлен новый инструмент передачи файлов по локальной сети под названием Warpinator, улучшена поддержка приложений Electron.', '2020-09-30 19:20:31', 'КАК ОБНОВИТЬ LINUX MINT ДО 20', '1', '1', '1');
insert into post (is_active, moderation_status, text, time, title, view_count, moderator_id, user_id) values('1', 'ACCEPTED', 'MySQL - это одна из самых популярных систем управления базами данных, которая применяется почти везде, начиная от различных предприятий и промышленности и заканчивая организацией работы веб-сайтов.', '2020-10-01 19:20:31', 'УСТАНОВКА MYSQL В UBUNTU 20.04', '1', '1', '1');
insert into post (is_active, moderation_status, text, time, title, view_count, moderator_id, user_id) values('1', 'ACCEPTED', 'Большинство ноутбуков на рынке поставляется с операционной системой Windows или MacOS. Кроме этого есть варианты без операционной системы. Пользователи Linux после покупки ноутбука обычно устанавливают свой любимый дистрибутив параллельно имеющейся ОС или производят чистую установку.К счастью, некоторые производители устанавливают Linux на свои ноутбуки. Это позволяет получить уверенность в том, что все устройства заработают и не придётся искать подходящий драйвер. К тому же предустановленный Linux зачастую не добавляет стоимости ноутбуку и позволяет выполнять большую часть поставленных перед ним задач. В одной из предыдущих статей было рассказано как выбрать ноутбук для Linux, в этой же мы подобрали только тех производителей, ноутбуки которых реально приобрести в России, в том числе с доставкой из-за рубежа.', '2020-10-02 19:20:31', 'ПРОИЗВОДИТЕЛИ НОУТБУКОВ С LINUX', '1', '1', '1');
insert into post_comments (text, time, post_id, user_id) values('cool', '2020-09-23 19:20:31', '1', '1');
insert into post_comments (text, time, post_id, user_id) values('good', '2020-09-22 19:30:31', '2', '1');
