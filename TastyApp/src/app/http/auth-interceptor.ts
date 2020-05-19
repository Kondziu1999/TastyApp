import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    
    intercept(req: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>>{
        const idToken = localStorage.getItem("access_token");

        // if (idToken) {
            // const cloned = req.clone({
            //     headers: req.headers.set("Authorization",
            //         "Bearer " + idToken)
            // });
            const clon=req.clone({
                setHeaders: {
                    Authorization: `Bearer ${idToken}`
                    }
            });

            return next.handle(clon);
        // }
        // else {
        //     return next.handle(req);
        // }
    }

}
