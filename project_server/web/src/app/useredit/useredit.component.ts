import { Component, OnInit } from '@angular/core';
import { UserService } from '../shared/user.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'app-useredit',
  templateUrl: './useredit.component.html',
  styleUrls: ['./useredit.component.css'],
  providers: [UserService]
})
export class UsereditComponent implements OnInit {
  info: infomation[];
  userDetails;
  constructor(private userService: UserService,private router:Router) { }

  ngOnInit() {
    var check = localStorage.getItem('check');
    this.userService.getDetails(check).subscribe((res: any) => {
      this.info = [res];
      console.log(this.info);
    });
    this.userService.getUserProfile().subscribe(
      res => {
        this.userDetails = res['user'];
      },
      err => { 
        console.log(err);
        
      }
    );
  }
  goapplication(id: string ,fullName: string, email: string, password: string) {
    var check = localStorage.setItem('check', id);
  }

  onSubmit(form:NgForm){
    
    this.userService.putUser(form.value).subscribe((res) => {
    alert("수정 완료");
    this.router.navigateByUrl('/');
  })
  }

  }

  





interface infomation{
  _id: string;
  fullName: string;
  email: string;
  password: string;
 
}

