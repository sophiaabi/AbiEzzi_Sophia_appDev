//
//  ViewController.swift
//  project1
//
//  Created by Sophia AbiEzzi on 9/27/20.
//  Copyright © 2020 Sophia AbiEzzi. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate, UITableViewDataSource, UITableViewDelegate {

    struct FoodItem{
        var stringName: String
        var color: UIColor
        var numDays: Int
        init(stringName: String, color: UIColor, numDays: Int){
            self.stringName = stringName
            self.color = color
            self.numDays = numDays
        }
    }
    var foodItems: [FoodItem] = []
    @IBOutlet weak var dateInputBox: UITextField!
    private var datePicker : UIDatePicker?
    
    @IBOutlet weak var addButton: UIButton!
    @IBOutlet weak var foodInputBox: UITextField!
    @IBOutlet weak var tableView: UITableView!
    
    
    @IBAction func addFoodItem(_ sender: UIButton) {
        view.endEditing(true)
        if (foodInputBox.text == "") || (dateInputBox.text == "") {
        var alert = UIAlertController()
        if (foodInputBox.text == "") && (dateInputBox.text == "") {
            alert=UIAlertController(title:"Error", message:"Must enter food name and expiration date", preferredStyle: UIAlertController.Style.alert)
        } else if foodInputBox.text == "" {
            alert=UIAlertController(title:"Error", message:"Must enter food name", preferredStyle: UIAlertController.Style.alert)
        } else {
            alert=UIAlertController(title:"Error", message:"Must enter expiration date", preferredStyle: UIAlertController.Style.alert)
        }
        let cancel=UIAlertAction(title:"Cancel", style: UIAlertAction.Style.cancel, handler: nil)
        alert.addAction(cancel)
        present(alert, animated: true, completion: nil)
        }
        else {
            //process name and date- add to string
            let curDate = Date()
            let dateForm = DateFormatter()
            dateForm.dateFormat = "MM/dd/yyy"
            let expDate = dateForm.date(from: dateInputBox.text!)!
            if expDate < curDate {
                var alert = UIAlertController()
                alert=UIAlertController(title:"Error", message:"Must enter future date", preferredStyle: UIAlertController.Style.alert)
                let cancel=UIAlertAction(title:"Cancel", style: UIAlertAction.Style.cancel, handler: nil)
                alert.addAction(cancel)
                present(alert, animated: true, completion: nil)
            }
            else {
                let daysToExpire = Calendar.current.dateComponents([.day], from: curDate, to: expDate).day! + 1
                var foodColor : UIColor
                if daysToExpire < 3 {
                    foodColor = UIColor.red
                }
                else if daysToExpire < 6 {
                    foodColor = UIColor.systemYellow
                }
                else {
                    foodColor = UIColor.green
                }
                var foodItem: String = ""
                if daysToExpire > 1{
                    foodItem = foodInputBox.text! + "- " + String(daysToExpire) + " Days"
                }
                else{
                    foodItem = foodInputBox.text! + "- " + String(daysToExpire) + " Day"
                }
                foodItems.append(FoodItem(stringName: foodItem, color: foodColor, numDays: daysToExpire))
                foodItems = foodItems.sorted(by: {$0.numDays < $1.numDays})
                tableView.reloadData()
                foodInputBox.text = ""
                dateInputBox.text = ""
            }
            
        }
    }
    
    //date picker inside a text box tutorial: https://www.youtube.com/watch?v=aa-lNWUVY7g
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        foodInputBox.delegate = self
        //setting up date picker inside text field
        datePicker = UIDatePicker()
        datePicker?.datePickerMode = .date
        datePicker?.addTarget(self, action: #selector(ViewController.dateChanged(datePicker:)), for: .valueChanged)
        let tapping = UITapGestureRecognizer(target: self, action: #selector(ViewController.viewTapped(gestureRecognizer:)))
        view.addGestureRecognizer(tapping)
        dateInputBox.inputView = datePicker
        
    }
    public func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true;
    }
    //dismiss keyboard when anywhere on screen is tapped
    @objc func viewTapped(gestureRecognizer: UITapGestureRecognizer){
        view.endEditing(true)
    }
    //display date picker value in text field with desired format
    @objc func dateChanged(datePicker: UIDatePicker) {
        let dateForm = DateFormatter()
        dateForm.dateFormat = "MM/dd/yyy"
        dateInputBox.text = dateForm.string(from: datePicker.date)
    }

    //table view tutorial: https://www.youtube.com/watch?v=CO93s3CmMiY
    func numberOfSectionsInTableView(tableView : UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return foodItems.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "foodCell", for: indexPath)
        let foodItem = foodItems[indexPath.row]
        cell.textLabel?.text = foodItem.stringName
        cell.textLabel?.textColor = foodItem.color
        return cell
    }
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        
        if editingStyle == .delete {
            foodItems.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .bottom)
        }
    }
}

