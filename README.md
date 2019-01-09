# react-native-log-to-file
Log message to file in react native, work for Android.


## Installation
Install package from npm

```
npm install --save react-native-log-to-file
```
or
```
yarn add react-native-log-to-file
```
Link to your project
```
react-native link
```
## Usage
```javascript
import log from 'react-native-log-to-file';
log.logToFile('app started').then(res=>{
    console.log(res);
})
```
The log file locate in 'applicationName/applicationName_log.log'.
