import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { PixelDetailComponent } from '../pixel-detail/pixel-detail.component';

const appRoutes: Routes = [
  {
    path: "/pixel",
    component: PixelDetailComponent
  },
  {
    path: "",
    component: PixelDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}