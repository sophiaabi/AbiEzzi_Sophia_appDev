//
//  ViewController.swift
//  daVinci
//
//  Created by Sophia AbiEzzi on 9/1/20.
//  Copyright © 2020 Sophia AbiEzzi. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var artImage: UIImageView!
    
    @IBOutlet weak var brainText: UITextField!
    
    @IBAction func chooseLeft(_ sender: UIButton) {
        if sender.tag == 1{
            artImage.image = UIImage(named: "brain1")
            brainText.text = "This is the left brain."
        }
        else if sender.tag == 2{
            artImage.image = UIImage(named: "brain2")
            brainText.text = "This is the right brain."
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


}

