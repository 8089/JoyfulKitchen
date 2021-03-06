package com.joyful.joyfulkitchen.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.joyful.joyfulkitchen.model.FoodType;

import com.joyful.joyfulkitchen.model.Food;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "tb_food".
*/
public class FoodDao extends AbstractDao<Food, Long> {

    public static final String TABLENAME = "tb_food";

    /**
     * Properties of entity Food.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FoodId = new Property(0, Long.class, "foodId", true, "food_id");
        public final static Property FoodName = new Property(1, String.class, "foodName", false, "food_name");
        public final static Property FoodImg = new Property(2, String.class, "foodImg", false, "food_img");
        public final static Property Energy = new Property(3, double.class, "energy", false, "ENERGY");
        public final static Property Protein = new Property(4, double.class, "protein", false, "PROTEIN");
        public final static Property Fat = new Property(5, double.class, "fat", false, "FAT");
        public final static Property Carbohydrate = new Property(6, double.class, "carbohydrate", false, "CARBOHYDRATE");
        public final static Property Fiber = new Property(7, double.class, "fiber", false, "FIBER");
        public final static Property Cholesterol = new Property(8, double.class, "cholesterol", false, "CHOLESTEROL");
        public final static Property FoodTypeId = new Property(9, Long.class, "foodTypeId", false, "food_type_id");
        public final static Property CreateTime = new Property(10, java.util.Date.class, "createTime", false, "create_time");
        public final static Property UpdateTime = new Property(11, java.util.Date.class, "updateTime", false, "update_time");
    }

    private DaoSession daoSession;


    public FoodDao(DaoConfig config) {
        super(config);
    }
    
    public FoodDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"tb_food\" (" + //
                "\"food_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: foodId
                "\"food_name\" TEXT," + // 1: foodName
                "\"food_img\" TEXT," + // 2: foodImg
                "\"ENERGY\" REAL NOT NULL ," + // 3: energy
                "\"PROTEIN\" REAL NOT NULL ," + // 4: protein
                "\"FAT\" REAL NOT NULL ," + // 5: fat
                "\"CARBOHYDRATE\" REAL NOT NULL ," + // 6: carbohydrate
                "\"FIBER\" REAL NOT NULL ," + // 7: fiber
                "\"CHOLESTEROL\" REAL NOT NULL ," + // 8: cholesterol
                "\"food_type_id\" INTEGER," + // 9: foodTypeId
                "\"create_time\" INTEGER," + // 10: createTime
                "\"update_time\" INTEGER);"); // 11: updateTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"tb_food\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Food entity) {
        stmt.clearBindings();
 
        Long foodId = entity.getFoodId();
        if (foodId != null) {
            stmt.bindLong(1, foodId);
        }
 
        String foodName = entity.getFoodName();
        if (foodName != null) {
            stmt.bindString(2, foodName);
        }
 
        String foodImg = entity.getFoodImg();
        if (foodImg != null) {
            stmt.bindString(3, foodImg);
        }
        stmt.bindDouble(4, entity.getEnergy());
        stmt.bindDouble(5, entity.getProtein());
        stmt.bindDouble(6, entity.getFat());
        stmt.bindDouble(7, entity.getCarbohydrate());
        stmt.bindDouble(8, entity.getFiber());
        stmt.bindDouble(9, entity.getCholesterol());
 
        Long foodTypeId = entity.getFoodTypeId();
        if (foodTypeId != null) {
            stmt.bindLong(10, foodTypeId);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(11, createTime.getTime());
        }
 
        java.util.Date updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindLong(12, updateTime.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Food entity) {
        stmt.clearBindings();
 
        Long foodId = entity.getFoodId();
        if (foodId != null) {
            stmt.bindLong(1, foodId);
        }
 
        String foodName = entity.getFoodName();
        if (foodName != null) {
            stmt.bindString(2, foodName);
        }
 
        String foodImg = entity.getFoodImg();
        if (foodImg != null) {
            stmt.bindString(3, foodImg);
        }
        stmt.bindDouble(4, entity.getEnergy());
        stmt.bindDouble(5, entity.getProtein());
        stmt.bindDouble(6, entity.getFat());
        stmt.bindDouble(7, entity.getCarbohydrate());
        stmt.bindDouble(8, entity.getFiber());
        stmt.bindDouble(9, entity.getCholesterol());
 
        Long foodTypeId = entity.getFoodTypeId();
        if (foodTypeId != null) {
            stmt.bindLong(10, foodTypeId);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(11, createTime.getTime());
        }
 
        java.util.Date updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindLong(12, updateTime.getTime());
        }
    }

    @Override
    protected final void attachEntity(Food entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Food readEntity(Cursor cursor, int offset) {
        Food entity = new Food( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // foodId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // foodName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // foodImg
            cursor.getDouble(offset + 3), // energy
            cursor.getDouble(offset + 4), // protein
            cursor.getDouble(offset + 5), // fat
            cursor.getDouble(offset + 6), // carbohydrate
            cursor.getDouble(offset + 7), // fiber
            cursor.getDouble(offset + 8), // cholesterol
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // foodTypeId
            cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)), // createTime
            cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)) // updateTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Food entity, int offset) {
        entity.setFoodId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFoodName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFoodImg(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEnergy(cursor.getDouble(offset + 3));
        entity.setProtein(cursor.getDouble(offset + 4));
        entity.setFat(cursor.getDouble(offset + 5));
        entity.setCarbohydrate(cursor.getDouble(offset + 6));
        entity.setFiber(cursor.getDouble(offset + 7));
        entity.setCholesterol(cursor.getDouble(offset + 8));
        entity.setFoodTypeId(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setCreateTime(cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)));
        entity.setUpdateTime(cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Food entity, long rowId) {
        entity.setFoodId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Food entity) {
        if(entity != null) {
            return entity.getFoodId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Food entity) {
        return entity.getFoodId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getFoodTypeDao().getAllColumns());
            builder.append(" FROM tb_food T");
            builder.append(" LEFT JOIN tb_food_type T0 ON T.\"food_type_id\"=T0.\"food_type_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Food loadCurrentDeep(Cursor cursor, boolean lock) {
        Food entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        FoodType foodType = loadCurrentOther(daoSession.getFoodTypeDao(), cursor, offset);
        entity.setFoodType(foodType);

        return entity;    
    }

    public Food loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Food> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Food> list = new ArrayList<Food>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Food> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Food> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
