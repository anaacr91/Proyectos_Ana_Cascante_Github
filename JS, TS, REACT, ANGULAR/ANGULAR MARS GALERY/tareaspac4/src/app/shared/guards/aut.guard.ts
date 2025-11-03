import { CanActivateFn } from '@angular/router';

export const autGuard: CanActivateFn = (route, state) => {
  
  let escorrecto: boolean= false;
  const persona = sessionStorage.getItem('registro');
  if (persona) {
    try {
      const personaParsed = JSON.parse(persona);
      if (personaParsed.user && personaParsed.nombre && personaParsed.mail) {
        escorrecto=true;
      }
    } catch (error) {
      console.log(error);
    }
  }
  return escorrecto;
};
