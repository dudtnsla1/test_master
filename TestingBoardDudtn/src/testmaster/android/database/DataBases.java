package testmaster.android.database;
import android.provider.BaseColumns;

public final class DataBases {
	
    public static final class CreateDB implements BaseColumns{
        public static final String COUNT = "count";
        public static final String PORT = "port";
        public static final String VALUE = "value";
        public static final String DATE = "date";
        public static final String NAME = "name";
        public static final String KIND = "kind";
        public static final String _DATA_TABLENAME = "ADCDatabase";
        public static final String _INFO_TABLENAME = "InfoDatabase";
        public static final String _CREATE_DATA = 
            "create table "+_DATA_TABLENAME+"("
                    +_ID+" integer primary key autoincrement, "    
                    +COUNT+" integer not null , "
                    +PORT+" integer not null , "
                    +VALUE+" integer not null , "
                    +DATE+" double not null );";
        public static final String _CREATE_INFO = 
                "create table "+_INFO_TABLENAME+"("
                        +_ID+" integer primary key autoincrement, "
                        +KIND+" int not null , "
                        +NAME+" text , "
                        +DATE+" double not null );";
    }
}
