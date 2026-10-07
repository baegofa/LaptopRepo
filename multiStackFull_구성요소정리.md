# multiStackFull 구성요소 정리

배열 하나를 여러 구간으로 나누어 **n개의 스택을 동시에 담는** 제네릭 자료구조(Multiple Stack).
한 스택이 포화되면 이웃 스택의 여유 공간을 **밀어서(shift) 가져오는** 동적 경계 조정을 지원한다.

---

## 1. 설계 원리

### 1.1 영역 분할

`data` 배열 하나를 `boundaryArr`로 잘라 각 스택에 구간을 배정한다.

```
             stack 0        stack 1        stack 2        stack 3
data  :  [ ■ ■ · · · ][ · · · · · ][ ■ ■ ■ ■ ■ ][ ■ ■ · · · ]
idx   :    0 1 2 3 4    5 6 7 8 9   10 ... 14    15 ... 19
             ↑top=1         ↑top=4         ↑top=14      ↑top=16
boundary:  0            5            10           15          20
```

| 개념 | 정의 |
|---|---|
| 스택 `i`의 **영역** | `boundaryArr[i]` ~ `boundaryArr[i+1] - 1` |
| 스택 `i`의 **원소** | `boundaryArr[i]` ~ `topArr[i]` |
| 스택 `i`의 **여유 칸 수** | `boundaryArr[i+1] - 1 - topArr[i]` |

### 1.2 빈 스택 규약

`topArr[i] == boundaryArr[i] - 1` 을 **빈 상태**로 둔다.
덕분에 `push`는 `data[++topArr[index]]`, `pop`은 `data[topArr[index]--]` 로 분기 없이 처리된다.

### 1.3 불변식 (Invariant)

모든 연산 전후에 아래가 성립해야 한다.

```
boundaryArr[0]           == 0
boundaryArr[stackNo]     == data.length
boundaryArr[i]           <= boundaryArr[i+1]                  (0 <= i < stackNo)
boundaryArr[i] - 1       <= topArr[i] <= boundaryArr[i+1] - 1 (0 <= i < stackNo)
```

마지막 조건이 **스택이 자기 영역을 벗어나지 않음**을 보장한다.

---

## 2. 필드

### 2.1 상수

| 이름 | 타입 | 값 | 역할 |
|---|---|---|---|
| `MAX_SIZE` | `static final int` | `100` | 기본 생성자가 사용하는 배열 크기 |
| `MAX_STACK_NO` | `static final int` | `10` | 기본 생성자가 사용하는 스택 개수 |
| `ERROR_DETECT` | `static final boolean` | `true` | 디버그 출력 토글용 플래그 (현재 미사용) |

`static final` 이므로 인스턴스 수와 무관하게 메모리에 한 번만 존재한다.

### 2.2 인스턴스 변수

| 이름 | 타입 | 접근 | 길이 | 역할 |
|---|---|---|---|---|
| `data` | `E[]` | `private` | `size` | 모든 스택의 원소를 담는 실제 저장소 |
| `topArr` | `int[]` | package | `stackNo` | 각 스택의 top 위치(절대 인덱스) |
| `boundaryArr` | `int[]` | package | `stackNo + 1` | 각 스택 영역의 시작 경계. 마지막 칸은 배열 끝 감시값(sentinel) |

**`boundaryArr`의 길이가 `stackNo + 1`인 이유**
스택 `i`의 상한을 `boundaryArr[i+1]`로 읽기 위해서다. 마지막 스택(`i == stackNo-1`)도 같은 식으로 처리하려면 `boundaryArr[stackNo]`가 존재해야 하고, 여기에 `size`를 넣어 둔다. 이 하나의 감시값으로 `isFull`에서 마지막 스택을 따로 분기할 필요가 없어진다.

**별도의 `n`(스택 수) 필드를 두지 않은 이유**
`topArr.length`가 곧 스택 수이므로 중복 상태를 만들지 않는다. 두 값이 어긋날 위험이 원천 차단된다.

---

## 3. 생성자

### 3.1 `multiStackFull()`

기본 설정으로 위임한다.

```java
this(MAX_SIZE, MAX_STACK_NO);   // 100칸 / 10스택
```

생성자 간 위임(`this(...)`)이므로 초기화 로직이 한 곳에만 존재한다.

### 3.2 `multiStackFull(int size, int stackNo)`

**파라미터** `size` 전체 배열 크기 / `stackNo` 스택 개수

**로직**

1. 저장소 할당
   - `data = (E[]) new Object[size]`
   - 자바는 `new E[size]`가 불가능(type erasure)하므로 `Object[]`를 만들어 캐스팅한다. unchecked 경고가 발생하지만 `data`가 `private`이라 외부로 새지 않아 안전하다.
2. 제어 배열 할당
   - `topArr = new int[stackNo]`
   - `boundaryArr = new int[stackNo + 1]`
3. 감시값 설정
   - `boundaryArr[stackNo] = size`
4. 구간 균등 분할 — `i = 0 .. stackNo-1`
   - 구간 크기 `seg = size / stackNo`
   - `boundaryArr[i] = seg * i` → 영역 시작
   - `topArr[i] = seg * i - 1` → 빈 상태 (`boundaryArr[i] - 1`)

**나머지 칸 처리**
`size`가 `stackNo`로 나누어떨어지지 않으면 `boundaryArr[stackNo] = size`를 3단계에서 먼저 넣었기 때문에 **남는 칸이 전부 마지막 스택에 귀속**된다. 예: `size=22, stackNo=4` → 경계 `0, 5, 10, 15, 22`, 마지막 스택만 7칸.

**결과 예시** `(20, 4)`

| | stack 0 | stack 1 | stack 2 | stack 3 |
|---|---|---|---|---|
| `boundaryArr` | 0 | 5 | 10 | 15 (+ `[4]=20`) |
| `topArr` | -1 | 4 | 9 | 14 |
| 상태 | EMPTY | EMPTY | EMPTY | EMPTY |

---

## 4. 상태 조회 메서드

### 4.1 `int size(int index)`

**반환** `topArr[index] - boundaryArr[index]`
**예외** `IndexOutOfBoundsException` — `index < 0 || index > topArr.length`

**로직**

1. `index` 유효성 검사 → 벗어나면 메시지와 함께 예외를 던진다.
2. `top - boundary` 를 반환한다.

**반환값 해석**
`top - boundary` 는 **원소 수 - 1** 이다. 빈 스택은 `-1`, 1개면 `0`, 5개면 `4`를 반환한다.

**예외를 쓴 이유**
특수 반환값(`-2` 등)으로 오류를 알리면 호출한 쪽이 검사를 **잊어도 컴파일이 통과**해 잘못된 값이 그대로 흘러간다. 예외는 무시할 수 없고 발생 지점이 스택 트레이스에 남는다. `IndexOutOfBoundsException`은 `RuntimeException` 계열(unchecked)이라 호출 측에 `try-catch`를 강제하지 않는다 — 잘못된 index는 외부 요인이 아니라 **프로그래머의 버그**이므로 unchecked가 적절하다.

### 4.2 `boolean isFull(int index)`

**반환** `topArr[index] == boundaryArr[index+1] - 1`

top이 자기 영역의 **마지막 칸**에 도달했는지 본다. `boundaryArr[index+1]`을 읽으므로 감시값 덕에 마지막 스택도 동일하게 처리된다.

### 4.3 `boolean isEmpty(int index)`

**반환** `topArr[index] == boundaryArr[index] - 1`

빈 스택 규약(1.2)을 그대로 검사한다.

### 4.4 `E top(int index)`

**반환** top 위치의 원소. 빈 스택이면 `null`

**로직**

1. `isEmpty(index)` → `null` 반환
2. 아니면 `data[topArr[index]]` 반환 (**제거하지 않음**)

---

## 5. 변경 메서드

### 5.1 `void push(int index, E e)`

**로직**

1. `isFull(index)` 인가?
   - **아니면** → `data[++topArr[index]] = e` (top을 먼저 올리고 그 자리에 저장)
   - **그렇다면** → `adjustMultiStack(index)` 호출
     - `true` (공간 확보 성공) → `data[++topArr[index]] = e`
     - `false` (전체 포화) → `"All stack is full"` 출력, 저장하지 않음
2. 종료

**`++topArr[index]` (전위 증가)인 이유**
빈 스택의 top이 `boundary - 1`이므로, 먼저 1을 더하면 정확히 `boundary`(영역 첫 칸)를 가리킨다. 전위/후위를 바꾸면 영역 밖을 가리키게 된다.

### 5.2 `E pop(int index)`

**반환** 제거된 원소. 빈 스택이면 `null`

**로직**

1. `isEmpty(index)` → `null` 반환
2. 아니면 `data[topArr[index]--]` 반환

**`topArr[index]--` (후위 감소)인 이유**
`data[top]`을 **먼저 읽고** 그 다음 top을 내린다. 전위 감소면 아래 칸을 읽게 된다.

**비고** 제거된 칸의 참조는 `data`에 남아 있다(지연 삭제). 유효 범위(`boundary ~ top`) 밖이므로 동작에는 영향이 없다.

---

## 6. `boolean adjustMultiStack(int index)`

이 클래스의 핵심. 포화된 스택 `index`를 위해 **이웃의 여유 공간을 빼앗아 온다.**

**반환** 공간 확보 성공 여부
**부수 효과** `data`의 원소 이동, `topArr` / `boundaryArr` 갱신

### 6.1 핵심 개념

> **공간 확보 = 원소 이동 + 경계 이동**

두 가지가 반드시 한 쌍으로 일어나야 한다. `data`만 옮기고 `boundaryArr`를 그대로 두면 `isFull`의 판정 기준이 변하지 않으므로 그 스택은 여전히 포화 상태다. 반대로 경계만 옮기면 원소가 엉뚱한 스택의 영역에 남는다.

### 6.2 전체 흐름

```
① index 유효성 검사  →  실패 시 메시지 출력 + false
② 왼쪽에서 여유 공간 찾기  →  찾으면 왼쪽으로 밀고 true
③ 오른쪽에서 여유 공간 찾기  →  찾으면 오른쪽으로 밀고 true
④ 양쪽 모두 실패  →  false
```

②가 실패하면 **그대로 ③으로 진행**한다(두 탐색이 배타적 분기가 아님). 그래서 "왼쪽은 꽉 찼지만 오른쪽에 여유가 있는" 상황도 처리된다.

### 6.3 ① 유효성 검사

```java
if(0 > index || index >= topArr.length) {
    System.out.println("wrong index parameter in adjustMultiStack method");
    return false;
}
```

`||` 이어야 한다. `&&`로 쓰면 두 조건이 동시에 참일 수 없어 검사가 영구 무효가 된다.
상한은 `>=` — `index == topArr.length`는 유효한 스택이 아니다.

### 6.4 ② 왼쪽 밀기

**조건** `index > 0` (왼쪽에 스택이 존재)

**탐색** `i = index` 부터 `1` 까지 감소하며 `isFull(i-1)`이 `false`인 지점을 찾는다.
즉 **이웃 `i-1`에 여유가 있는지**를 본다. `i`가 1까지 내려가므로 `isFull(0)`까지 검사되어 stack 0도 후보에 포함된다.

**이동** 스택을 한 칸씩 왼쪽으로 옮긴다.

```java
for(int j = boundaryArr[k]; j <= topArr[k]; j++) {
    data[j-1] = data[j];      // 한 칸 왼쪽으로 복사
}
topArr[k]--;
boundaryArr[k]--;
```

**순회 방향이 증가(`j++`)인 이유**
왼쪽으로 옮길 때 감소 순회를 쓰면 아직 읽지 않은 칸을 미리 덮어쓴다.

```
감소 순회 (틀림) : [21 22 23 24 25]
  j=14: data[13]=data[14] → 21 22 23 25 25
  j=13: data[12]=data[13] → 21 22 25 25 25   ← 23이 사라짐
  결과 : 25 25 25 25 25

증가 순회 (맞음) : [21 22 23 24 25]
  j=10: data[9] =data[10] → 21 ...
  j=11: data[10]=data[11] → 22 ...
  결과 : 21 22 23 24 25  (한 칸 왼쪽으로)
```

**이동 결과**
대상 스택의 `boundaryArr`가 1 줄고 `topArr`도 1 줄어든다. 상한 `boundaryArr[index+1]`은 그대로이므로 **영역이 1칸 늘어나 포화가 풀린다.** 여유를 내준 `i-1`은 원소는 그대로이고 영역만 1칸 줄어든다.

### 6.5 ③ 오른쪽 밀기

**조건** `index < topArr.length`

**탐색** `i = index` 부터 증가하며 `isFull(i)`가 `false`인 지점을 찾는다.
`i == index`일 때는 포화 상태이므로 자동으로 건너뛰고, 실제로는 `index+1` 이후에서 찾게 된다.

**이동**

```java
for(int j = topArr[i]; j >= boundaryArr[i]; j--) {
    data[j+1] = data[j];      // 한 칸 오른쪽으로 복사
}
topArr[i]++;
boundaryArr[i]++;
```

**순회 방향이 감소(`j--`)인 이유**
②와 정반대다. 오른쪽으로 옮길 때는 **높은 인덱스부터** 복사해야 덮어쓰기가 발생하지 않는다.

> **방향 규칙** — 옮기는 방향의 **반대쪽 끝에서부터** 읽는다.
> 왼쪽으로 밀면 낮은 인덱스부터, 오른쪽으로 밀면 높은 인덱스부터.

**이동 결과**
`boundaryArr[i]`가 1 늘어나므로 그 **왼쪽 스택의 영역이 1칸 확장**된다.

### 6.6 ④ 실패

양쪽 탐색 모두 여유 스택을 찾지 못하면 `false`. 모든 스택이 포화이므로 진짜 오버플로우다. `push`가 이를 받아 `"All stack is full"`을 출력한다.

### 6.7 동작 예시

`(20, 4)` 구성에서 stack 2가 FULL, stack 1이 EMPTY인 상태에서 `push(2, 99)`.

```
[전]  idx :  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19
      val :  1  2  .  .  .  .  .  .  .  . 21 22 23 24 25 31 32  .  .  .
      stk :  0  0  0  0  0  1  1  1  1  1  2  2  2  2  2  3  3  3  3  3
      stack 2 : 영역[10..14] top=14  FULL

      ② 왼쪽 탐색 : i=2 → isFull(1)==false → stack 2를 왼쪽으로 한 칸

[후]  idx :  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19
      val :  1  2  .  .  .  .  .  .  . 21 22 23 24 25 99 31 32  .  .  .
      stk :  0  0  0  0  0  1  1  1  1  2  2  2  2  2  2  3  3  3  3  3
                                    ↑ 경계가 10 → 9 로 이동
      stack 1 : 영역[ 5.. 8]  (5칸 → 4칸)
      stack 2 : 영역[ 9..14] top=14  (5칸 → 6칸, 99 저장 완료)
      stack 3 : 영역[15..19]  변화 없음
```

---

## 7. 시간 복잡도

| 메서드 | 복잡도 | 비고 |
|---|---|---|
| `size` / `isFull` / `isEmpty` / `top` | **O(1)** | 산술 비교만 수행 |
| `pop` | **O(1)** | |
| `push` (여유 있음) | **O(1)** | 대부분의 경우 |
| `push` (밀기 발생) | **O(S)** | `S` = 이동되는 스택의 원소 수 |
| `adjustMultiStack` | **O(n + S)** | `n` = 스택 개수(탐색), `S` = 원소 이동 |

`push`의 최악 비용은 밀기에서 나오지만, 밀기는 포화 시에만 발생하므로 **분할 상환(amortized) 관점에서는 평균 O(1)에 가깝다.**

**공간 복잡도** `O(size + n)` — `data` 외에 제어 배열 두 개뿐이며 추가 버퍼를 쓰지 않는다(in-place).

---

## 8. 제네릭 사용

```java
public class multiStackFull<E>
```

클래스에 타입 파라미터 `E`를 선언해 **담을 타입을 사용하는 쪽이 결정**하게 한다.

```java
multiStackFull<Integer> a = new multiStackFull<>(20, 4);
multiStackFull<String>  b = new multiStackFull<>(20, 4);
```

알고리즘(push / pop / 밀기)은 담는 타입과 무관하게 동일하므로, 타입별로 클래스를 복제할 필요가 없다. 대신 기본형은 담을 수 없어 `int` 대신 `Integer`를 써야 한다(오토박싱으로 자동 변환).

**제약** 타입 소거(type erasure) 때문에 `new E[size]`가 불가능해 생성자에서 `(E[]) new Object[size]` 캐스팅이 필요하다.

---

## 9. 구성요소 요약표

| 분류 | 이름 | 시그니처 | 복잡도 |
|---|---|---|---|
| 상수 | `MAX_SIZE` | `static final int = 100` | — |
| 상수 | `MAX_STACK_NO` | `static final int = 10` | — |
| 상수 | `ERROR_DETECT` | `static final boolean = true` | — |
| 필드 | `data` | `private E[]` | — |
| 필드 | `topArr` | `int[]` | — |
| 필드 | `boundaryArr` | `int[]` | — |
| 생성자 | — | `multiStackFull()` | O(n) |
| 생성자 | — | `multiStackFull(int, int)` | O(n) |
| 조회 | `size` | `int size(int)` | O(1) |
| 조회 | `isFull` | `boolean isFull(int)` | O(1) |
| 조회 | `isEmpty` | `boolean isEmpty(int)` | O(1) |
| 조회 | `top` | `E top(int)` | O(1) |
| 변경 | `push` | `void push(int, E)` | O(1) ~ O(S) |
| 변경 | `pop` | `E pop(int)` | O(1) |
| 재배치 | `adjustMultiStack` | `boolean adjustMultiStack(int)` | O(n + S) |
