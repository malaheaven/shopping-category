-- -----------------------------------------------------
-- DROP Table
-- -----------------------------------------------------
DROP TABLE IF EXISTS musinsa.category;

-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
create table if not exists musinsa.category
(
    id          bigint auto_increment primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    depth       bigint       null,
    name        varchar(255) not null,
    parent_id   bigint       null,
    constraint unique_category_name_depth
        unique (name, depth),
    constraint fk_parent_id
        foreign key (parent_id) references musinsa.category (id)
);
