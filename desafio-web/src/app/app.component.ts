import { Component } from '@angular/core';
import { LoadingService } from './loading.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'desafio-web';
  property: any;
  isLoading = false;

  constructor(private loadingService: LoadingService) {
    this.loadingService.loading$.subscribe(loading => {
      this.isLoading = loading;
    });
  }
}
