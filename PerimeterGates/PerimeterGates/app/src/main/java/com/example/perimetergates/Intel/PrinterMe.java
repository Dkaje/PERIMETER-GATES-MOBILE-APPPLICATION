package com.example.perimetergates.Intel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.io.FileOutputStream;
import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class PrinterMe extends PrintDocumentAdapter {
    private PrintedPdfDocument mDoc;
    private Context mCon;
    private View mVie;

    public PrinterMe(Context context, View view){
        mCon=context;
        mVie=view;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback, Bundle extras) {

        mDoc = new PrintedPdfDocument(mCon, newAttributes);

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                .Builder("print_output.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(1);

        PrintDocumentInfo info = builder.build();
        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination,
                        CancellationSignal cancellationSignal,
                        WriteResultCallback callback) {

        // Start the page
        PdfDocument.Page page = mDoc.startPage(0);
        // Create a bitmap and put it a canvas for the view to draw to. Make it the size of the view
        Bitmap bitmap = Bitmap.createBitmap(mVie.getWidth(), mVie.getHeight (),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mVie.draw(canvas);
        // create a Rect with the view's dimensions.
        Rect src = new Rect(0, 0, mVie.getWidth(), mVie.getHeight ());
        // get the page canvas and measure it.
        Canvas pageCanvas = page.getCanvas();
        float pageWidth = pageCanvas.getWidth();
        float pageHeight = pageCanvas.getHeight();
        // how can we fit the Rect src onto this page while maintaining aspect ratio?
        float scale = Math.min(pageWidth/src.width(), pageHeight/src.height());
        float left = pageWidth / 2 - src.width() * scale / 2;
        float top = pageHeight / 2 - src.height() * scale / 2;
        float right = pageWidth / 2 + src.width() * scale / 2;
        float bottom = pageHeight / 2 + src.height() * scale / 2;
        RectF dst = new RectF(left, top, right, bottom);

        pageCanvas.drawBitmap(bitmap, src, dst, null);
        mDoc.finishPage(page);

        try {
            mDoc.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            mDoc.close();
            mDoc = null;
        }
        callback.onWriteFinished(new PageRange[]{new PageRange(0, 0)});
    }
}
