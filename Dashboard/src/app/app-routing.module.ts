import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PixelDetailComponent } from './pixel-detail/pixel-detail.component';
import { IndexComponent } from './index/index.component';

const routes: Routes = [
  {
    path: "pixel/:id",
    component: PixelDetailComponent
  },
  {
    path: "",
    component: IndexComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
