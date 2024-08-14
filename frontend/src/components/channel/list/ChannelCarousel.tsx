import React, { useRef } from 'react';
import styled from 'styled-components';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination, Scrollbar, Autoplay } from 'swiper/modules';
import SwiperCore from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';
import UserChannelCard from './UserChannelCard';

SwiperCore.use([Scrollbar, Autoplay]);

interface ChannelCarouselProps {
   slides: Array<{
      id: string;
      name: string;
      followCount: number;
      imgUrl: string;
   }>;
   handleChannelClick: (channelId: string) => void;
}

const ChannelCarousel: React.FC<ChannelCarouselProps> = ({ slides, handleChannelClick }) => {
   const swiperRef = useRef<SwiperCore>();

   const getSlidesPerView = () => {
      if (window.innerWidth >= 1440) return 5;
      if (window.innerWidth >= 1024) return 4;
      if (window.innerWidth >= 768) return 3;
      if (window.innerWidth >= 480) return 2;
      return 1;
   };

   const slidesPerView = getSlidesPerView();
   const shouldEnablePaginationAndAutoplay = slides.length > slidesPerView;

   return (
      <SwiperContainer>
         <Swiper
            onSwiper={(swiper => {
               swiperRef.current = swiper;
            })}
            loop={false}
            spaceBetween={0}
            pagination={shouldEnablePaginationAndAutoplay ? {
               clickable: true,
            } : false}
            modules={[Pagination]}
            slidesOffsetAfter={10}
            autoplay={shouldEnablePaginationAndAutoplay ? {
               delay: 2500,
               disableOnInteraction: false,
               pauseOnMouseEnter: true,
            } : false}
            breakpoints={{
               320: { // 작은 화면
                  slidesPerView: 1,
               },
               480: {
                  slidesPerView: 2,
                  spaceBetween: 10,
               },
               768: { // 태블릿
                  slidesPerView: 3,
                  spaceBetween: 10,
               },
               1024: { // 작은 데스크탑
                  slidesPerView: 4,
                  spaceBetween: 20,
               },
               1440: { // 큰 데스크탑
                  slidesPerView: 5,
                  spaceBetween: 30,
               },
            }}
         >
            {slides.map((slide) => (
               <SwiperSlideStyled key={slide.id}>
                  <UserChannelCardStyled
                     name={slide.name}
                     followCount={slide.followCount}
                     img={slide.imgUrl}
                     onClick={(e) => {
                        e.stopPropagation();
                        handleChannelClick(slide.id);
                     }}
                  />
               </SwiperSlideStyled>
            ))}
         </Swiper>
      </SwiperContainer>

   );
};

const SwiperContainer = styled.div`
  padding: 10px; /* Adjust padding to provide extra space */
`;

const SwiperSlideStyled = styled(SwiperSlide)`
  padding: 10px; /* Add padding to the slides themselves */
  box-sizing: border-box; /* Ensure padding is included in the slide dimensions */
`;

const UserChannelCardStyled = styled(UserChannelCard)`
  overflow: visible; /* Ensure content is not clipped */
`;


export default ChannelCarousel;
