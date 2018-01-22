package com.nikitagordia.aplay.Database;

/**
 * Created by root on 1/22/18.
 */

public class AudioDBShema {
    public static final class AudioInfoTable {
        public static final String NAME = "audioInfo";

        public static final class Columns {

            public static final String URL = "url";
            public static final String COUNT = "count";
            public static final String DATE = "date";
        }
    }
}
