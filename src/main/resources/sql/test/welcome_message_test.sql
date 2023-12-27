delete from welcome_message
where welcome_message_id in (198123123);

insert into welcome_message (welcome_message_id, guild_id, channel_id, message)
values (198123123, 1076316414421499924, 1125462364452565003, 'Hello world!');