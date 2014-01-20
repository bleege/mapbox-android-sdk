/**
 * @author Brad Leege <bleege@gmail.com>
 * Created on 1/20/14 at 12:49 PM
 */

package com.mapbox.mapboxsdk;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import org.osmdroid.DefaultResourceProxyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;

public class MapBoxResourceProxyImpl extends DefaultResourceProxyImpl
{
    private static final Logger logger = LoggerFactory.getLogger(MapBoxResourceProxyImpl.class);
    private Resources mResources;
    private DisplayMetrics mDisplayMetrics;

    public MapBoxResourceProxyImpl(Context pContext)
    {
        super(pContext);
        mResources = pContext.getResources();
        mDisplayMetrics = mResources.getDisplayMetrics();
        if (DEBUGMODE)
        {
            logger.debug("mDisplayMetrics=" + mDisplayMetrics);
        }
    }

    public Bitmap getMapBoxBitmap(final String resource) {
        InputStream is = null;
        try {
            final String resName = resource + ".png";
            is = MapBoxResourceProxyImpl.class.getResourceAsStream(resName);
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resName
                        + ". Did you add the resources directory to the classpath? "
                        + "I told you to add the damn resource files to the classpath");
            }
            BitmapFactory.Options options = null;
/*
            if (mDisplayMetrics != null) {
                options = getBitmapOptions();
            }
*/
            return BitmapFactory.decodeStream(is, null, options);
        } catch (final OutOfMemoryError e) {
            logger.error("OutOfMemoryError getting bitmap resource: " + resource);
            System.gc();
            // there's not much we can do here
            // - when we load a bitmap from resources we expect it to be found
            throw e;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (final IOException ignore) {
                }
            }
        }
    }

    public Drawable getMapBoxDrawable(final String resource)
    {
        return mResources != null
                ? new BitmapDrawable(mResources, getMapBoxBitmap(resource))
                : new BitmapDrawable(getMapBoxBitmap(resource));
    }
}
