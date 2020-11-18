import { RouterModule, Routes } from '@angular/router';
import {InitialSetupComponent} from "./initial-setup/initial-setup.component";

const routes: Routes = [
  {path : '', component : InitialSetupComponent}
];

export const AppRouting = RouterModule.forRoot(routes);
