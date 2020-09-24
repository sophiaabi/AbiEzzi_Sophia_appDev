//
//  ViewController.swift
//  Lab3
//
//  Created by Sophia AbiEzzi on 9/10/20.
//  Copyright © 2020 Sophia AbiEzzi. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var caption: UILabel!
    @IBOutlet weak var image: UIImageView!
    @IBOutlet weak var topLabel: UILabel!
    @IBOutlet weak var sizeLabel: UILabel!
    @IBOutlet weak var capitalSwitch: UISwitch!
    
    
    
    @IBAction func changeContent(_ sender: UISegmentedControl) {
        if sender.selectedSegmentIndex == 0{
            image.image = UIImage(named: "tulip")
            caption.text = "Tulip festival in Washington State"
        }else if sender.selectedSegmentIndex == 1{
            image.image = UIImage(named: "hobbit")
            caption.text = "Countryside of southern Iceland"
        }else if sender.selectedSegmentIndex == 2{
            image.image = UIImage(named: "cinque")
            caption.text = "Cinque Terre, Italy"
        }
        updateSwitch(capitalSwitch)
    }
    
    @IBAction func updateSwitch(_ sender: UISwitch) {
        if sender.isOn{
            //capitalize
            caption.text = caption.text?.uppercased()
            caption.textColor = UIColor.systemPink
        }else {
            //lowercase
            caption.text = caption.text?.lowercased()
            caption.textColor = UIColor.black
        }
    }
    
    @IBAction func updateSize(_ sender: UISlider) {
        let fontsize=sender.value
        
        sizeLabel.text=String(format: "%.0f", fontsize)
        
        let fontSizeCGFloat=CGFloat(fontsize)
        topLabel.font=UIFont.systemFont(ofSize: fontSizeCGFloat)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

}

