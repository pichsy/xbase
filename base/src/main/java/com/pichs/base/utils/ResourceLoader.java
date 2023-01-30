package com.pichs.base.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 资源强制获取帮助类。
 * 通过名字获取相关资源。
 */
public class ResourceLoader {
    private final static String DRAWABLE_CLASS_NAME = "drawable";
    private final static String MIPMAP_CLASS_NAME = "mipmap";
    private final static String ID_CLASS_NAME = "id";
    private final static String LAYOUT_CLASS_NAME = "layout";
    private final static String XML_CLASS_NAME = "xml";
    private final static String ANIM_CLASS_NAME = "anim";
    private final static String STYLE_CLASS_NAME = "style";
    private final static String STRING_CLASS_NAME = "string";
    private final static String COLOR_CLASS_NAME = "color";
    private final static String RAW_CLASS_NAME = "raw";
    private final static String ARRAY_CLASS_NAME = "array";
    private final static String DIMEN_CLASS_NAME = "dimen";

    private Configuration mConfiguration = new Configuration();

    private static ResourceLoader mInstance = null;
    private Context mContext = null;


    private Resources mResources = null;

    private LayoutInflater mInflater = null;

    private int ErrorCode = 0;

    private ResourceLoader(Context context) {
        mContext = context;
        mResources = mContext.getResources();
        mInflater = LayoutInflater.from(mContext);
    }

    public static ResourceLoader getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ResourceLoader(context);
        }
        return mInstance;
    }

    /*
     * 取Color资源
     */
    public int getColor(String colorname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(colorname, COLOR_CLASS_NAME, mContext.getPackageName());
            return mResources.getColor(id);
        } else {
            return ErrorCode;
        }
    }

    /*
     * 取Color资源
     */
    public int getColorRes(String colorname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(colorname, COLOR_CLASS_NAME, mContext.getPackageName());
            int colorRes = mResources.getColor(id);
            return colorRes;
        } else {
            return ErrorCode;
        }
    }

    /*
     * 取 String 资源
     */
    public String getString(String strname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(strname, STRING_CLASS_NAME, mContext.getPackageName());
            if (id == 0) {
                return null;
            }
            return mResources.getString(id);
        } else {
            return null;
        }

    }

    public String getString(String strname, Object... formatArgs) {
        final String raw = getString(strname);
        return String.format(mConfiguration.locale, raw, formatArgs);
    }


    private int getStringForId(String strname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(strname, STRING_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }

    }

    /*
     * 取Drawable资源
     */

    public Drawable getDrawable(String drawname) {

        if (mResources != null) {
            int id = mResources.getIdentifier(drawname, DRAWABLE_CLASS_NAME, mContext.getPackageName());
            if (id == 0) {
                return null;
            }
            return mResources.getDrawable(id);
        } else {
            return null;
        }
    }

    public int getDrawableForId(String drawname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(drawname, DRAWABLE_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }

    public int getLayoutForId(String layoutname) {

        if (mResources != null) {
            int id = mResources.getIdentifier(layoutname, LAYOUT_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }

    }

    /*
     * 取Layout资源
     */
    public View getLayoutForView(String layoutname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(layoutname, LAYOUT_CLASS_NAME, mContext.getPackageName());
            if ((mInflater != null) && (id != 0)) {
                return mInflater.inflate(id, null);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /*
     * 取id资源
     */
    public int getId(String idname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(idname, ID_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }

    /*
     * 获取Raw资源
     */
    public int getRaw(String rawName) {
        if (mResources != null) {
            int id = mResources.getIdentifier(rawName, RAW_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }

    /*
     * 取Bitmap 资源
     */
    public Bitmap getDrawableBitmap(String imgname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(imgname, DRAWABLE_CLASS_NAME, mContext.getPackageName());
            if (id == 0) {
                return null;
            }
            Bitmap bitmap = BitmapFactory.decodeResource(mResources, id);
            return bitmap;
        } else {
            return null;
        }
    }

    public int getStyle(String stylename) {
        if (mResources != null) {
            int id = mResources.getIdentifier(stylename, STYLE_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }


    public int getAnim(String animname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(animname, ANIM_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }

    public XmlResourceParser getXmlForParser(String xmlname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(xmlname, XML_CLASS_NAME, mContext.getPackageName());
            if (id == 0) {
                return null;
            }
            return mResources.getXml(id);
        } else {
            return null;
        }
    }

    public int getXmlForId(String xmlname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(xmlname, XML_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }

    public int getArrayId(String arrayName) {
        if (mResources != null) {
            int id = mResources.getIdentifier(arrayName, ARRAY_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }

    public int[] getIntArray(String arrayName) {
        int arrayId = getArrayId(arrayName);
        return mResources.getIntArray(arrayId);
    }

    public String[] getStringArray(String arrayName) {
        int arrayId = getArrayId(arrayName);
        return mResources.getStringArray(arrayId);
    }

    public int getDimenId(String dimenName) {
        if (mResources != null) {
            int id = mResources.getIdentifier(dimenName, DIMEN_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }

    public int getDimensionPixelSize(String dimenName) {
        if (mResources != null) {
            int id = getDimenId(dimenName);
            return mResources.getDimensionPixelSize(id);
        } else {
            return ErrorCode;
        }
    }

    public int getMipmapForId(String mipmapName) {
        if (mResources != null) {
            int id = mResources.getIdentifier(mipmapName, MIPMAP_CLASS_NAME, mContext.getPackageName());
            return id;
        } else {
            return ErrorCode;
        }
    }

    public Bitmap getMipmapBitmap(String mipmapName) {
        if (mResources != null) {
            int id = mResources.getIdentifier(mipmapName, MIPMAP_CLASS_NAME, mContext.getPackageName());
            if (id == 0) {
                return null;
            }
            return BitmapFactory.decodeResource(mResources, id);
        } else {
            return null;
        }
    }


    /*
     * 取 mipmap目录下的Drawable资源
     */
    public Drawable getDrawableInMipmap(String drawname) {
        if (mResources != null) {
            int id = mResources.getIdentifier(drawname, MIPMAP_CLASS_NAME, mContext.getPackageName());
            if (id == 0) {
                return null;
            }
            return mResources.getDrawable(id);
        } else {
            return null;
        }
    }

}
