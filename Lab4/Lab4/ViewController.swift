//
//  ViewController.swift
//  Lab4
//
//  Created by Sophia AbiEzzi on 9/24/20.
//

import UIKit

extension UIViewController {
    func hideKeyboard() {
     let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action:    #selector(UIViewController.dismissKeyboard))
      tap.cancelsTouchesInView = false
      view.addGestureRecognizer(tap)
    }
    @objc func dismissKeyboard() {
       view.endEditing(true)
    }
}
class ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var totalDue: UILabel!
    @IBOutlet weak var shakeStepper: UIStepper!
    @IBOutlet weak var friesStepper: UIStepper!
    @IBOutlet weak var burgerStepper: UIStepper!
    
    @IBOutlet weak var shakeNum: UILabel!
    @IBOutlet weak var friesNum: UILabel!
    @IBOutlet weak var burgerNum: UILabel!
    
    @IBOutlet weak var tipAmount: UITextField!
    @IBOutlet var calculateTotal: UIView!
    
    @IBAction func calcTotal(_ sender: UIButton) {
        
        //for an alert to screen
        if (shakeStepper.value==0 && friesStepper.value==0 && burgerStepper.value==0) || (tipAmount.text == "") {
            var alert = UIAlertController()
            if (shakeStepper.value==0 && friesStepper.value==0 && burgerStepper.value==0) && (tipAmount.text == "") {
                alert=UIAlertController(title:"Error", message:"Must order at least 1 food item & Must enter tip amount", preferredStyle: UIAlertController.Style.alert)
            } else if tipAmount.text == "" {
                alert=UIAlertController(title:"Error", message:"Must enter tip amount", preferredStyle: UIAlertController.Style.alert)
            } else {
                alert=UIAlertController(title:"Error", message:"Must order at least 1 food item", preferredStyle: UIAlertController.Style.alert)
            }
            let cancel=UIAlertAction(title:"Cancel", style: UIAlertAction.Style.cancel, handler: nil)
            alert.addAction(cancel)
            present(alert, animated: true, completion: nil)
        }
        else{
            var amount:Float
            var pct:Float
            amount = 0.0
            pct = Float(tipAmount.text!)!/100
            let burgerAmount = Float(burgerStepper.value) * 5.0
            let friesAmount = Float(friesStepper.value) * 2.5
            let shakeAmount = Float(shakeStepper.value) * 3.0
            amount = burgerAmount + friesAmount + shakeAmount
            let tipAmount = amount * pct
            amount = amount + tipAmount
            let currencyFormat = NumberFormatter()
            currencyFormat.numberStyle=NumberFormatter.Style.currency
            totalDue.text=currencyFormat.string(from: NSNumber(value: amount))
        }
    }

    @IBAction func updateNumShakes(_ sender: UIStepper) {
        shakeNum.text = String(format: "%.0f", shakeStepper.value)
    }
    @IBAction func updateNumFries(_ sender: UIStepper) {
        friesNum.text = String(format: "%.0f", friesStepper.value)
    }
    @IBAction func updateNumBurgers(_ sender: UIStepper) {
        burgerNum.text = String(format: "%.0f", burgerStepper.value)
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    override func viewDidLoad() {
        tipAmount.delegate = self
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        self.hideKeyboard()
    }


}

