package com.bdpartener.wallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "MyMoney";
	private static final int DB_VERSION = 1;

	public static final String TABLE_EXPENSE = "Expense";
	public static final String COL_NAME_E = "eName";
	public static final String COL_NOTE_E = "eNote";
	public static final String COL_DATE_E = "eDate";
	public static final String COL_AMOUNT_E = "eAmount";
	private static final String STRING_CREATE_E = "CREATE TABLE "
			+ TABLE_EXPENSE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME_E + " TEXT, " + COL_NOTE_E + " TEXT, " + COL_DATE_E
			+ " DATE," + COL_AMOUNT_E + " FLOAT);";

	public static final String TABLE_INCOME = "Income";
	public static final String COL_NAME_I = "iName";
	public static final String COL_NOTE_I = "iNote";
	public static final String COL_DATE_I = "iDate";
	public static final String COL_AMOUNT_I = "iAmount";
	private static final String STRING_CREATE_I = "CREATE TABLE "
			+ TABLE_INCOME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME_I + " TEXT, " + COL_NOTE_I + " TEXT, " + COL_DATE_I
			+ " DATE," + COL_AMOUNT_I + " FLOAT);";

	public static final String TABLE_BUDGET = "Budget";
	public static final String COL_NAME_B = "bName";
	public static final String COL_NOTE_B = "bNote";
	public static final String COL_DATE_B = "bDate";
	public static final String COL_AMOUNT_B = "bAmount";
	private static final String STRING_CREATE_B = "CREATE TABLE "
			+ TABLE_BUDGET + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME_B + " TEXT, " + COL_NOTE_B + " TEXT, " + COL_DATE_B
			+ " DATE, " + COL_AMOUNT_B + " FLOAT);";

	public static final String TABLE_TOTAL = "Total";
	public static final String COL_MONTH = "tMonth";
	public static final String COL_YEAR = "tYear";
	public static final String COL_TOTAL = "tAmount";
	private static final String STRING_CREATE_T = "CREATE TABLE " + TABLE_TOTAL
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_MONTH
			+ "TEXT, " + COL_YEAR + "INTEGER, " + COL_TOTAL + " FLOAT);";

	public static final String TABLE_REGISTRATION = "Registration";
	public static final String COL_EMAIL = "email";
	public static final String COL_PASS = "password";
	public static final String COL_QUE = "question";
	public static final String COL_ANS = "answer";
	private static final String STRING_CREATE_R = "CREATE TABLE "+ TABLE_REGISTRATION
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_EMAIL
			+ " TEXT," + COL_PASS + " TEXT," + COL_QUE + "TEXT," + COL_ANS + "TEXT);";


	public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("PAG", "OnCreate Method is calling");
		db.execSQL(STRING_CREATE_E);
		db.execSQL(STRING_CREATE_I);
		db.execSQL(STRING_CREATE_B);
		db.execSQL(STRING_CREATE_T);
		db.execSQL(STRING_CREATE_R);

		ContentValues cv = new ContentValues();
		cv.put(COL_TOTAL, 0);
		db.insert(TABLE_TOTAL, null, cv);
		// db.execSQL(STRING_CREATE_TABLE_CAT_EVENT);
		// db.execSQL(STRING_CREATE_TABLE_CAT_INTREST);
		// db.execSQL(STRING_CREATE_TABLE_CAT_SALARY);
		// db.execSQL(STRING_CREATE_TABLE_CAT_SALE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("PAG", "OnUpgrade Method is calling");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOTAL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT_INTREST);
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT_EVENT);
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT_SALARY);
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT_SALE);
		onCreate(db);
	}
}
