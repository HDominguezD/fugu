import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { SigninService } from '../services/signin.service';
import { Http } from '@angular/http';

interface Restaurant {
    restaurantname: string;
    address?: string;
    city?: string;
    foodtype?: string;
    phone?: number;
    email: string;
    password: string;
    confirmPassword: string;
}
interface User {
    username: string;
    age?: number;
    favouritefood?: string;
    description?: string;
    email: string;
    password: string;
    confirmPassword: string;
}
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {
 // dataclient: any;
  public user: User;
  public restaurant: Restaurant;
  ngOnInit() {
    this.user = {
      username: '',
      email: '',
      password: '',
      confirmPassword: ''
    };
    this.restaurant = {
      restaurantname: '',
      email: '',
      password: '',
      confirmPassword: ''
    };
  }

  constructor(private loginService: LoginService, private router: Router
  , private Signinservice: SigninService, private http: Http) { }


  logIn(event: any, user: string, pass: string) {

    event.preventDefault();

    this.loginService.logInUser(user, pass).subscribe(
      u => console.log(u),
      error => console.log('Invalid user or password')
    );
    this.loginService.logInRestaurant(user, pass).subscribe(
      u => console.log(u),
      error => console.log('Invalid restaurant or password')
    );
  }

  logOut() {
    this.loginService.logOut().subscribe(
      response => { },
      error => console.log('Error when trying to log out: ' + error)
    );
  }
  visitProfile() {
    if (this.loginService.user.roles.indexOf('ROLE_RESTAURANT') !== -1) {
      this.router.navigate(['/new/private-restaurant/']);
    } else {
      this.router.navigate(['/new/private-client/']);
    }
  }

  sendForm(){
    //CLIENT
    console.log(this.user.username);
    console.log(this.user.email);
    console.log(this.user.age);
    console.log(this.user.favouritefood);
    console.log(this.user.description);
    console.log(this.user.password);
    console.log(this.user.confirmPassword);

    let dataclient = {"name": this.user.username, 
                      "password": this.user.password,
                      "email": this.user.email,
                      "age": this.user.age,
                      "description": this.user.description,
                      "favouriteFood": this.user.favouritefood,
                      "roles": "ROLE_USER"          
                    }
    this.http.post('https://localhost:8443/api/clients/signin', dataclient).subscribe(
      response => console.log(response),
      error => console.error(error)
    );
    //RESTAURANT
    console.log(this.restaurant.restaurantname);
    console.log(this.restaurant.address);
    console.log(this.restaurant.city);
    console.log(this.restaurant.foodtype);
    console.log(this.restaurant.phone);
    console.log(this.restaurant.email);
    console.log(this.restaurant.password);
    console.log(this.restaurant.confirmPassword);
  }

}
