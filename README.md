![image (3)](https://github.com/user-attachments/assets/d1e2429e-bf96-47e6-a524-0fe2345a0463)
![image (3)](https://github.com/user-attachments/assets/d1e2429e-bf96-47e6-a524-0fe2345a0463)

## 🏢 Happy Travel – ERP 기업 자원 관리 시스템

> 중소기업의 업무 흐름 자동화 및 자원 통합 관리를 목표로 한 내부용 ERP 시스템 구축 프로젝트
> 
> 
> **DB 설계부터 인증, 메인 UI, 스케줄러 기능까지** 직접 구현하며 실제 서비스 구조와 유사한 백오피스 개발 경험
> 

---

### 📌 프로젝트 개요

- **기술스택**: Spring Boot · JPA · MySQL · Vue.js · JWT · Spring Scheduler · Docker
- **주요 기능**: 로그인 인증 / 대시보드 / 공지사항 / 자동 스케줄러 / 관리자 기능 등
- **기여 비중**: 백엔드 및 DB 설계 주도 (개발 기여도 약 40%)

---

### 👨‍💻 개인 기여

### ✅ DB 설계 및 백엔드 구조 정의

- 사용자, 일정, 공지사항, 로그인 이력 등 핵심 테이블 **ERD 설계 및 관계 정의**
- JPA 기반 엔티티 구조 설계 및 DTO·Service·Controller 계층 구성

### 🔐 로그인 인증 시스템 개발

- JWT 기반 로그인/로그아웃 기능 구현 및 토큰 관리
- 사용자 권한(Role) 분기 처리 및 비정상 접근 시 예외 핸들링
- 관리자/일반 사용자 구분 및 보안 흐름 설계

### 🧩 메인 대시보드 기능 구현

- 주요 일정 요약, 공지사항 목록, 오늘자 데이터 등을 **Vue.js + Axios**로 시각화
- **백엔드 API 및 응답 구조 직접 설계 및 연결**

### ⏰ 스케줄러 기능 개발

- Spring Scheduler를 활용해 반복 업무(데이터 초기화, 리마인드 알림 등) 자동화
- 작업 실패 대비 예외 처리 및 로그 기록 기능 포함

---

## 🔐 로그인 보안 처리 방식 (Spring Boot + JWT)

1. **JWT(Json Web Token)** 기반 로그인 인증 구현
    - 클라이언트에서 로그인 요청 시, 서버에서 사용자 인증 후 **Access Token + Refresh Token** 발급
    - JWT에는 사용자 ID, 권한(Role), 만료 시간 등 정보를 포함
2. **보안 처리 흐름**
    - 모든 요청은 Spring Security의 필터 체인을 거쳐 JWT 유효성 검사를 수행
    - 유효하지 않은 토큰은 `401 Unauthorized` 응답 처리
    - Access Token 만료 시 Refresh Token으로 재발급 요청 가능하도록 구성
3. **Spring Security 설정**
    
    ```java
    java
    복사편집
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    ```
    

---

## ⏰ 스케줄러 개발 방식 (Spring Scheduler)

1. **Spring Boot의 `@Scheduled` 어노테이션 사용**
    - 매일 자정마다 예약 데이터 초기화 또는 알림 메일 발송 등의 반복 업무 처리
    - `cron` 표현식을 활용한 유연한 주기 설정
2. **예시 코드**
    
    ```java
    java
    복사편집
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void resetDailySchedule() {
        log.info("매일 00시 스케줄 초기화 실행");
        scheduleRepository.clearExpired();
    }
    
    ```
    
3. **보완 사항**
    - **스케줄 실패에 대비한 로깅 및 예외 처리** 포함
    - 향후 고가용성 환경을 위해 `Spring Batch` 또는 `Quartz` 확장 고려

### 🎯 배운 점 및 성과

- **실제 서비스용 ERP 구조 경험**
    
    사용자 권한·스케줄링·데이터 정합성 등 **업무 시스템이 요구하는 안정성과 유연성**의 기준을 실무적으로 배움
    
- **DB 설계가 전체 흐름에 미치는 영향 체감**
    
    테이블 설계 시점에서의 실수가 API와 화면 흐름까지 영향을 미치기에, **처음부터 구조를 탄탄하게 잡는 중요성**을 실감
    
- **Vue.js + Spring Boot 연동 실습 경험**
    
    프론트와 백엔드 간 API 연동, CORS 처리, Axios 사용 등 전반적인 Full-Stack 흐름 경험
    
- **실행력 중심의 백엔드 구현 경험 강화**
    
    로그인 → 메인 → 스케줄러 → 전체 흐름이 이어지는 기능을 직접 설계하며 **서비스 흐름을 처음부터 끝까지 책임지는 개발 경험**을 축적
