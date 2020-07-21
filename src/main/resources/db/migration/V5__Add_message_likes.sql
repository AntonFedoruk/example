create table message_likes (
    user_id    bigint not null,
    message_id bigint not null,
    primary key (user_id, message_id),
    foreign key (user_id) references usr (id),
    foreign key (message_id) references message (id)
);