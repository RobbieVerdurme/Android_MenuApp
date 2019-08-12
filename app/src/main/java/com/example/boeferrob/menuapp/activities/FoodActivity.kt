package com.example.boeferrob.menuapp.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.boeferrob.menuapp.model.Food
import com.example.boeferrob.menuapp.model.Ingredient
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.fragments.Adapter.IngredientRecyclerAdapter
import com.example.boeferrob.menuapp.ui.FoodActivityViewModel
import com.example.boeferrob.menuapp.utils.*
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.android.synthetic.main.content_foodactivity.*
import kotlinx.android.synthetic.main.item_add_ingredient_list.*

/**
 * See the selected recipe or add a recipe
 */
class FoodActivity : AppCompatActivity() {
    /************************************************variablen*********************************************************/
    private var foodPosition = POSITION_NOT_SET
    private var logedin = POSITION_NOT_SET
    private lateinit var food: Food
    lateinit var foodActivityViewModel: FoodActivityViewModel

    /************************************************Override**********************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        toolbar.setLogo(R.drawable.menu_logo_navbar)
        toolbar.titleMarginStart = 50
        setSupportActionBar(toolbar)

        /**
         * create viewmodel
         */
        foodActivityViewModel = ViewModelProviders.of(this).get(FoodActivityViewModel::class.java)

        /**
         * food position in the list
         */
        foodPosition = savedInstanceState?.getInt(FOOD_POSITION, POSITION_NOT_SET)?:intent.getIntExtra(FOOD_POSITION, POSITION_NOT_SET)

        /**
         * check if logged in
         */
        logedin = intent.getIntExtra(LOGIN, POSITION_NOT_SET)

        /**
         *get food from list
         */
        if(foodPosition != POSITION_NOT_SET){
            food = foodActivityViewModel.getFood(foodPosition)
        }else {
            food = Food(0,"", ArrayList<Ingredient>(), "")
        }

        /**
         * display the food on the screen using the correct input fields
         */
        displayFood()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(FOOD_POSITION, foodPosition)
    }

    override fun onResume() {
        super.onResume()
        listFoodIngredients.adapter?.notifyDataSetChanged()
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.foodactionsmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when(id){
            R.id.action_save -> {
                saveFood()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    /************************************************Methods***********************************************************/
    private fun displayFood() {
        txtTitleFood.setText(food.name)
        txtDescriptionFood.setText(food.discritpion)
        configAdapterListFoodIngredients()
    }

    private fun configAdapterListFoodIngredients(){
        val swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))
        val  deleteIcon: Drawable = ContextCompat.getDrawable(this,R.drawable.ic_delete_black_24dp)!!
        val adapter = IngredientRecyclerAdapter(this,food.ingredients)
        val layouManager = LinearLayoutManager(this)

        listFoodIngredients.adapter = adapter
        listFoodIngredients.layoutManager = layouManager
        listFoodIngredients.addItemDecoration(DividerItemDecoration(listFoodIngredients.context, layouManager.orientation))

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false //niet nodig voor dit programma
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                (adapter as IngredientRecyclerAdapter).removeItem(p0)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemvView = viewHolder.itemView
                val iconMargin = (itemvView.height - deleteIcon.intrinsicHeight) / 2

                if(dX >0){
                    swipeBackground.setBounds(itemvView.left, itemvView.top, dX.toInt(), itemvView.bottom)
                    deleteIcon.setBounds(itemvView.left + iconMargin, itemvView.top + iconMargin, itemvView.left + iconMargin + deleteIcon.intrinsicWidth, itemvView.bottom - iconMargin)
                }else{
                    swipeBackground.setBounds(itemvView.left + dX.toInt(), itemvView.top, itemvView.right, itemvView.bottom)
                    deleteIcon.setBounds(itemvView.right - iconMargin - deleteIcon.intrinsicWidth, itemvView.top + iconMargin, itemvView.right - iconMargin, itemvView.bottom - iconMargin)
                }
                swipeBackground.draw(c)

                c.save()
                if(dX > 0 ) {
                    c.clipRect(itemvView.left, itemvView.top, dX.toInt(), itemvView.bottom)
                } else {
                    c.clipRect(itemvView.left + dX.toInt(), itemvView.top, itemvView.right, itemvView.bottom)
                }

                deleteIcon.draw(c)

                c.restore()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(listFoodIngredients)

        /**
         * making the add bar to add ingredients
         */
        makeAddIngredient(adapter)
    }


    private fun makeAddIngredient(adapter: IngredientRecyclerAdapter){
        spinnerMesurement.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, MESUREMENTLIST)
        imgAddIngredient.setOnClickListener {
            //check if fields are filled in
            if(checkRequiredFieldsIngredient(txtName, txtQuantity)){
                //adding ingredient
                food.ingredients.add(
                    Ingredient(
                    txtName.text.toString(),
                    txtQuantity.text.toString().toInt(),
                    spinnerMesurement.selectedItem.toString())
                )
                adapter.notifyDataSetChanged()

                //clear textfields
                txtName.setText("")
                txtQuantity.setText("")
                spinnerMesurement.setSelection(0)
            }
        }
    }

    private fun saveFood() {
        if (food.name.isBlank() or food.discritpion.isBlank()){
            foodPosition = foodActivityViewModel.getLastIndexFood() + 1
            foodActivityViewModel.addFood(food)
        }

        if(checkRequiredFieldsFood()){
            food.name = txtTitleFood.text.toString()
            food.discritpion = txtDescriptionFood.text.toString()
            foodActivityViewModel.saveFood(food)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(LOGIN, logedin)
            startActivity(intent)
        }
    }

    //checks
    private fun checkRequiredFieldsFood(): Boolean{
        var check = true

        if(textFieldEmpty(txtTitleFood)){
            txtTitleFood.error = getString(R.string.required)
            check = false
        }

        return check
    }

    private fun checkRequiredFieldsIngredient(txtName: TextView, txtquantity: TextView): Boolean{
        if(txtName.text.toString().trim().isBlank() or txtquantity.toString().trim().isBlank()){
            Toast.makeText(this, R.string.checkIngredientFields, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun textFieldEmpty(textField: EditText): Boolean {
        val text = textField.text.toString()
        return text.trim() == ""
    }
}
