
# JBus

JBus is the final project for Object-Oriented Programming Lab in the third semester of my Computer Engineering degree at University of Indonesia. This repository is specifically for the front-end part of the JBus android application. To connect to the back-end, the app uses SpringBoot framework and JSon converter.

* Target SDK    : Android version 33
* Minimum SDK   : Android version 21
## Authors

- [@auli-aziz](https://www.github.com/auli-aziz)


## Requirements

For building and running the application you need:
* [JDK version 11](https://www.oracle.com/java/technologies/javase/jdk11-readme.html)
* Minimum of Java Version 11.0.20

## Additional Dependencies

1. Retrofit Library:
- Group ID: org.springframework.boot
- Version: 2.9.0

3. Gson Converter for Retrofit:
- Group ID: com.squareup.retrofit2:converter-gson
- Version: 2.9.0
## Project Structure

- **src/main/java/com/auliaAnugrahAzizJBusRD/jbus_android/array_adapter:** Contains array adapters to manage list items in list view.
- **src/main/java/com/auliaAnugrahAzizJBusRD/jbus_android/model:** Contains model to handle data fetched from the API service.
- **src/main/java/com/auliaAnugrahAzizJBusRD/jbus_android/request:** Contains API and Retrofit Client to connect to the back-end
- **src/main/java/com/auliaAnugrahAzizJBusRD/:** Contains android activities to display data.

## JBus Activities Preview
### Register
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184044616442458152/image.png?ex=658a8a7b&is=6578157b&hm=003c48dfb4d4fdb74d486ea0bd070cb2e006ad5171bef42a5e25f44d74c0224d&" alt="drawing" width="200"/>

### Login
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184047132244717588/image.png?ex=658a8cd2&is=657817d2&hm=9bfa5fc9ef271aff280b2f0c77e05ec51e77c5b016f8866d4969a6bb0f5686ae&" alt="drawing" width="200"/>

### Main
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184048183949660200/image.png?ex=658a8dcd&is=657818cd&hm=b3f8d00db80744a043acd1d899a48ade5afed2700f91611b9400182258eaadbd&" alt="drawing" width="200"/>
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184050465709768734/image.png?ex=658a8fed&is=65781aed&hm=88a3af41f141beeb9564033cf5133204ef14da275de8c9ebf364adcc54b54df7&" alt="drawing" width="200"/>

### About Me
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184047730717368371/image.png?ex=658a8d61&is=65781861&hm=28e575365357279bb766f57bb8c9193392ad53a328f07769a9f52bc2090d1ef0&" alt="drawing" width="198"/>
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184047420540190760/image.png?ex=658a8d17&is=65781817&hm=7ee41d86b6e52cfde32a394c29369607721660c7c84dcf3f3d0502b849a9745e&" alt="drawing" width="200"/>

### Register Renter
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184048848134471720/image.png?ex=658a8e6b&is=6578196b&hm=9ee9d61d431887826e22c95ee12e511994a6e349f0cb5abd03e141fe09ee57e1&" alt="drawing" width="198"/>

### Bus Detail
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184049398561374279/image.png?ex=658a8eef&is=657819ef&hm=98dae2b2af10656c6d2dbd700323f98a349ae21d6ea832d43eec877056010d3e&" alt="drawing" width="200"/>
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184049519407677500/image.png?ex=658a8f0c&is=65781a0c&hm=4356021cdab3f7d14e24082d20f4f300f21454edf4c9aa37df2587b849090e34&" alt="drawing" width="199"/>

### Make Booking
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184049975504670760/image.png?ex=658a8f78&is=65781a78&hm=e2dc2ff4dd69b69da5c221bb4969b17e3210dde422cdd3ceaa69123349455ea7&" alt="drawing" width="198"/>

### Payment History
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184051514956845086/image.png?ex=658a90e7&is=65781be7&hm=3fbd0ee0aad4ea01b3b71659ebb5d466f34f5aec2015d5063faec3e7fed1c1cf&" alt="drawing" width="198"/>

### Manage Bus
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184051569734471770/image.png?ex=658a90f4&is=65781bf4&hm=7dd89740e52f813855ca01b5cc712f6979b9aa2d5633f0a81bff4c459437759c&" alt="drawing" width="198"/>

### Add Bus
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184052139622932603/image.png?ex=658a917c&is=65781c7c&hm=ba4b8e2b3ff9c892f46660505fc076560972e7144dde4d909d2e9bf33bfe7154&" alt="drawing" width="198"/>

### Add Schedule
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184052230719012895/image.png?ex=658a9192&is=65781c92&hm=f02882b6ed32cfa5db6fb561d12016e368d9aa87da539d28b246d2289df7f535&" alt="drawing" width="198"/>
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184052321794150430/image.png?ex=658a91a8&is=65781ca8&hm=921a81347bf762783596b219a886414721eb467960ccb3fb7dbbe487d6d19ddf&" alt="drawing" width="196"/>
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184052417298440212/image.png?ex=658a91be&is=65781cbe&hm=08c3a99c558a7a1081f23dc49a122204acf13a02f63c70e3ac71053c050c1438&" alt="drawing" width="198"/>

### Payment Requests
<img src="https://cdn.discordapp.com/attachments/1087264258074628176/1184052521602392164/image.png?ex=658a91d7&is=65781cd7&hm=7847376632504b519c552b1b5a569be8132627981f6aa5e226ee847487a5f3b8&" alt="drawing" width="198"/>
