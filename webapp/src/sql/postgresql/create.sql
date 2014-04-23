
-- family tree
create sequence family_tree_sequence                        increment by 1 start 1;

create table family_tree (
    family_tree_id                  integer                     not null
  , name                            varchar(255)                not null
  , creation_date                   timestamp without time zone default current_date
  , modification_date               timestamp without time zone default current_date
  , constraint family_tree_pkey primary key (family_tree_id)
);

