package com.example.projetandroid2022.db;

public final class DBContract {
    public static class Form {
        //le nom de la BDD
        public static final String TABLE_NAME = "watchlist_entries";
        public static final String ID = "id";
        //ici l'id de la ressource
        public static final String ID_RESOURCE = "id_resource";
        //la date à laquelle la ressource à été vu :
        public static final String COLUMN_DATE = "date_of_viewing";
        //la note que l'utilisateur a donné à la ressource :
        public static final String COLUMN_RATING = "rating";
        //si la ressource est une série ou non :
        public static final String COLUMN_IS_SHOW = "is_show";

    }
}
