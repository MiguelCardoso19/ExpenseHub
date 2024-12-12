import {Component, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {SignInRequestDto} from '../../services/models';
import {AuthenticationControllerService} from '../../services/services/authentication-controller.service';
import {Router} from '@angular/router';
import {TokenService} from '../../services/token/token.service';
import {ErrorHandlingService} from '../../services/errorHandling/error-handling.service';
import {IdService} from '../../services/id/id.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  signInRequest: SignInRequestDto = {email: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationControllerService,
    private tokenService: TokenService,
    private errorHandlingService: ErrorHandlingService,
    private idService: IdService
  ) {
  }

  login() {
    this.errorMsg = [];
    this.authService.signIn({
      body: this.signInRequest
    }).subscribe({
      next: (res) => {
        if (res instanceof Blob) {
          res.text().then((text: string) => {
            const parsedResponse = JSON.parse(text);
            this.tokenService.token = parsedResponse.token as string;
            this.tokenService.refreshToken = parsedResponse.refreshToken as string;
            this.idService.id = parsedResponse.id as string;
            this.router.navigate(['dashboard']);
          }).catch((err) => {
            this.errorMsg.push('Error reading the Blob response: ' + err.message);
          });
        }
      },
      error: (err) => {
        this.errorMsg = this.errorHandlingService.handleError(err);
      }
    });
  }

  register(): void {
    this.router.navigate(['register'])
  }
}