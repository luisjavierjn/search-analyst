import { RouterModule, Routes } from '@angular/router';
import {AnalyzerComponent} from "./analyzer/analyzer.component";
import {InitialSetupComponent} from "./initial-setup/initial-setup.component";

const routes: Routes = [
  { path: 'analyzer', component: AnalyzerComponent },
  {path : '', component : InitialSetupComponent}
];

export const AppRouting = RouterModule.forRoot(routes);
