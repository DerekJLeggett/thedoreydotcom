import { Component, OnInit } from '@angular/core';
import { IPhoto } from './photo';
import { PhotoService } from './photo.service';
import { IAudio } from './audio';
import { AudioService } from './audio.service';
import { IVideo } from './video';
import { VideoService } from './video.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit{
  title = 'client';
  photos: IPhoto[] = [];
  songs: IAudio[] = [];
  videos: IVideo[] = [];
  page: number = 0;
  size: number = 12;
  imagePath: string;
  constructor(private photoService: PhotoService, private audioService: AudioService, private videoService: VideoService) { }

  ngOnInit() {
    this.getAllPhotos(this.page, this.size);
  }

  getAllPhotos(page: number, size: number) {
    this.photoService.getAllPhotos(page, size)
    .subscribe(response => this.photos = response);
  }

  showModal(event){
    console.log(event)
    this.imagePath = event.srcElement.currentSrc;
  }

  getAllSongs(){
    this.audioService.getAllSongs()
    .subscribe(response => this.songs = response);
  }

  getAllVideos(){
    this.videoService.getAllVideos()
    .subscribe(response => this.videos = response);
  }

  getNext(){
    this.page += 1;
    this.getAllPhotos(this.page, this.size);
  }

  getPrevious(){
    if(this.page > 0){
      this.page -= 1;
      this.getAllPhotos(this.page, this.size);
    }
  }
}
