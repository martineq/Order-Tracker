package com.example.uriel.ordertracker.App.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Brand;
import com.example.uriel.ordertracker.App.Model.GridAdapter;
import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Model.Product;
import com.example.uriel.ordertracker.App.Services.Impl.BrandService;
import com.example.uriel.ordertracker.App.Services.Impl.ProductService;
import com.example.uriel.ordertracker.App.Services.Interface.IBrandService;
import com.example.uriel.ordertracker.App.Services.Interface.IProductService;
import com.example.uriel.ordertracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderActivity extends DrawerActivity {

    private String brand_spinner[];
    private IProductService productService;
    private IBrandService brandService;
    GridView grid;
    GridAdapter gridAdapter;
    int clientId;
    boolean readOnly;
    private HashMap<Integer, String> order;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    public OrderActivity(){
        order = new HashMap<Integer, String>();
    }

    //Esta funcion guarda el estado en caso de que se rote el dispositivo
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        obtenerPedido();
        savedInstanceState.putSerializable("order", order);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        productService = new ProductService();
        brandService = new BrandService();

        final Activity context = this;
        FloatingActionButton verPedido = (FloatingActionButton) findViewById(R.id.ver_pedido);

        readOnly = getIntent().getExtras().getBoolean("ReadOnly");
        if(readOnly){
            verPedido.setVisibility(View.INVISIBLE);
        }else {
            clientId = getIntent().getExtras().getInt("clientId");
            String clientName = getIntent().getExtras().getString("clientName");

            verPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obtenerPedido();
                    double total = calcularTotal();

                    Intent intent = new Intent(context, ViewMyOrderActivity.class);
                    intent.putExtra("order", order);
                    intent.putExtra("total", total);
                    intent.putExtra("clientId", clientId);
                    startActivity(intent);
                }
            });
        }

        //cargar opciones de rubros
        ArrayList<Brand> brands = brandService.getAll();
        final ArrayList<Integer> brandsIds = new ArrayList<Integer>();
        brand_spinner = new String[brands.size() + 1];
        brand_spinner[0] = "TODAS";
        brandsIds.add(0);
        for(int i = 0; i < brands.size(); i++){
            Brand marca = brands.get(i);
            brand_spinner[i+1] = marca.getDescription();
            brandsIds.add(marca.getId());
        }

        final Spinner s = (Spinner) findViewById(R.id.brandSpinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.spinner_item, brand_spinner);
        s.setAdapter(spinnerAdapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String brand = s.getItemAtPosition(position).toString();
                int rubroId = brandsIds.get(position);
                //TODO: setBrand por id para ser mas prolijo
                setBrand(brand);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setBrand(s.getItemAtPosition(0).toString());

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            order = (HashMap<Integer, String>)savedInstanceState.getSerializable("order");

            for (int i = 0; i < grid.getChildCount(); i++) {
                View gridItem = (View) grid.getChildAt(i);
                int productId = Integer.valueOf(((TextView) gridItem.findViewById(R.id.idText)).getText().toString());
                String orderItem = order.get(productId);
                EditText qtt = (EditText) gridItem.findViewById(R.id.quantityText);
                String quantityOrdered = orderItem.split("&")[1];
                if (!qtt.equals("")) {
                    qtt.setText(quantityOrdered);
                }
            }
        }

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        configDrawerAfterCreate(savedInstanceState);
        setTitle("Arme su pedido");
    }

    public void setBrand(String brand){
        ArrayList<Product> products;

        if(brand.equals("TODAS")){
            products = productService.getAll();
        }else{
            products = productService.getByBrand(brand);
        }

        grid=(GridView)findViewById(R.id.gridView);
        obtenerPedido();
        gridAdapter = new GridAdapter(OrderActivity.this, products, order, readOnly);
        grid.setAdapter(gridAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });

    }

    private void obtenerPedido() {
        for (int i = 0; i < grid.getChildCount(); i++) {
            View gridItem = (View) grid.getChildAt(i);
            int productId = Integer.valueOf(((TextView) gridItem.findViewById(R.id.idText)).getText().toString());
            String product = ((TextView) gridItem.findViewById(R.id.descripcionText)).getText().toString();
            double price = Double.parseDouble(((TextView) gridItem.findViewById(R.id.priceText)).getText().toString().substring(1).replace(",", "."));
            Integer quantity = 0;
            String qtt = ((EditText) gridItem.findViewById(R.id.quantityText)).getText().toString();
            if (!qtt.equals("")) {
                quantity = Integer.valueOf(qtt);
            }
            if (quantity > 0) {
                order.put(productId, product + "&" + String.valueOf(quantity) + "&" + String.valueOf(price));
            }
        }
    }

    private double calcularTotal(){
        double total = 0;
        for(Map.Entry<Integer, String> item : order.entrySet()) {
            String value = item.getValue();
            total += Integer.valueOf(value.split("&")[1]) * Double.valueOf(value.split("&")[2]);
        }

        return total;
    }

    private void volver(){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("clientId", clientId);
        startActivity(intent);
    }

    public void zoomImageFromThumb(final View thumbView, Bitmap bitmap, int productId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(R.id.zoomImage);
        //expandedImageView.setImageBitmap(bitmap);
        switch (productId){
            case 1:
                expandedImageView.setImageResource(R.drawable.bandeja);
                break;
            case 2:
                expandedImageView.setImageResource(R.drawable.bandeja_doble);
                break;
            case 3:
                expandedImageView.setImageResource(R.drawable.especiero);
                break;
            case 4:
                expandedImageView.setImageResource(R.drawable.espejo);
                break;
            case 5:
                expandedImageView.setImageResource(R.drawable.esponja);
                break;
            case 6:
                expandedImageView.setImageResource(R.drawable.hielera);
                break;
            case 7:
                expandedImageView.setImageResource(R.drawable.jarra);
                break;
            case 8:
                expandedImageView.setImageResource(R.drawable.pinche_choclo);
                break;
            case 9:
                expandedImageView.setImageResource(R.drawable.pinches);
                break;
            case 10:
                expandedImageView.setImageResource(R.drawable.sarten);
                break;
        }

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(readOnly){
            super.onBackPressed();
        }else {
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(this, "Volver", "Esta seguro que descartar desea cancelar el pedido?", "Descartar", "Cancelar");

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    finish();
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }
    }
}
