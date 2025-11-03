import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rovers-marte',
  templateUrl: './rovers-marte.component.html',
  styleUrl: './rovers-marte.component.css'
})

export class RoversMarteComponent implements OnInit{

    nombreCamMostrar: String | undefined;
    imagenes: any[] | undefined;

  mostrarImagen(nombre: any) {
   
      this.http.get("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?api_key=DEMO_KEY&sol=0&camera=" +nombre)
      .subscribe((Response:any)=> {
        console.log(Response);
        this.imagenes= Response.photos;
        this.nombreCamMostrar= nombre;
      },   
      (error:HttpErrorResponse) =>{
        if (error.error instanceof ErrorEvent){
          alert (error.error.message);
        
        }else{
          alert (error.message);
        }
      }
      );
}
  
  idamostrar: Number | undefined;
  mostrarElemento(rover: any) {
    this.idamostrar= rover.id;
  }

  roversObtenidos: any[];
  constructor(private http:HttpClient){
    this.roversObtenidos= [];
  }

  ngOnInit(): void {
   this.http.get("https://api.nasa.gov/mars-photos/api/v1/rovers?api_key=DEMO_KEY")
      .subscribe((Response:any)=> {
        this.roversObtenidos =Response.rovers;
        console.log(Response.rovers);
      },   
      (error:HttpErrorResponse) =>{
        if (error.error instanceof ErrorEvent){
          alert (error.error.message);
        
        }else{
          alert (error.message);
        }
      }
      );

    }

}
